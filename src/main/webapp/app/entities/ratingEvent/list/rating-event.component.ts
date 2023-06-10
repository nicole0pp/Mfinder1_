import { ChangeDetectorRef, AfterViewChecked, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { filter, Observable } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRatingEvent } from '../rating-event.model';

import { ITEMS_PER_PAGE, PAGE_HEADER } from 'app/config/pagination.constants';
import { ASC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, RatingEventService } from '../service/rating-event.service';
import { RatingEventDeleteDialogComponent } from '../delete/rating-event-delete-dialog.component';
import { RatingEventFormGroup, RatingEventFormService } from '../update/rating-event-form.service';

import { finalize } from 'rxjs/operators';
import { EventService } from 'app/entities/event/service/event.service';
import { IEvent } from 'app/entities/event/event.model';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-rating',
  templateUrl: './rating-event.component.html',
  styleUrls: ['./rating-event.scss'],
})
export class RatingEventComponent implements OnInit {
  ratingEvent: IRatingEvent | null = null;
  ratingEvents?: IRatingEvent[] | undefined;
  event: IEvent | null = null;
  isLoading = false;
  isSaving = false;

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  account: Account | null = null;

  editForm: RatingEventFormGroup = this.ratingEventFormService.createRatingEventFormGroup();

  constructor(
    protected ratingService: RatingEventService,
    protected ratingEventFormService: RatingEventFormService,
    protected activatedRoute: ActivatedRoute,
    protected ratingEventService: RatingEventService,
    protected accountService: AccountService,
    protected eventService: EventService,
    protected router: Router,
    protected modalService: NgbModal,
    private cdr: ChangeDetectorRef
  ) {}

  trackId = (_index: number, item: IRatingEvent): number => this.ratingService.getRatingEventIdentifier(item);
  @ViewChild('commentsContainer', { static: false }) commentsContainer!: ElementRef;

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.ratingEvent = data.ratingEvent;
      if (this.ratingEvent && this.ratingEvent.event) {
        this.event = this.ratingEvent.event;
        this.loadRatingEventsByEventId(this.ratingEvent.event.id);
      } else {
        this.router.navigateByUrl(this.router.url);
      }
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }
  ngAfterViewChecked() {
    if (this.commentsContainer) {
      const element = this.commentsContainer.nativeElement;
      element.scrollTop = element.scrollHeight;
    }

    this.cdr.detectChanges();
  }

  previousState(): void {
    window.history.back();
  }

  delete(ratingEvent: IRatingEvent): void {
    const modalRef = this.modalService.open(RatingEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ratingEvent = ratingEvent;

    modalRef.closed.pipe(filter(reason => reason === ITEM_DELETED_EVENT)).subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onSaveSuccess();
      },
    });
  }

  getRatingsGroup() {
    let groups = [];
    const groupSize = 1;
    let totalEventsSize = this.ratingEvents?.length;

    if (totalEventsSize != null || totalEventsSize != undefined) {
      for (let i = 0; i < totalEventsSize; i += groupSize) {
        groups.push(this.ratingEvents?.slice(i, i + groupSize));
      }
    }
    return groups;
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }
  clearComment() {
    this.editForm.reset();
  }
  save(): void {
    this.isSaving = true;
    const ratingEvent = this.ratingEventFormService.getRating(this.editForm);
    if (ratingEvent.id !== null) {
      this.subscribeToSaveResponse(this.ratingEventService.update(ratingEvent));
    } else {
      this.activatedRoute.params.subscribe(params => {
        const eventId = params['eventId'];
        const eventRoute: Pick<IEvent, 'id'> = { id: eventId };
        ratingEvent.event = eventRoute;
        this.subscribeToSaveResponse(this.ratingEventService.create(ratingEvent));
      });
    }
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRatingEvent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  private loadRatingEventsByEventId(eventId: number): void {
    this.ratingEventService.getRatingsByEvent(eventId).subscribe(response => {
      this.ratingEvents = response.body || undefined;
    });
  }
  protected onSaveSuccess(): void {
    this.ngOnInit();
    this.clearComment();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  truncateEventDescription(text: string): string {
    if (text.length > 30) {
      return text.substring(0, 30) + '...';
    }
    return text;
  }

  truncateEventTitle(text: string): string {
    if (text.length > 20) {
      return text.substring(0, 20) + '...';
    }
    return text;
  }
}
