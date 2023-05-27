import { Component, OnInit } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvent } from '../event.model';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA, ITEM_SAVED_EVENT } from 'app/config/navigation.constants';
import { EntityArrayResponseType, EventService } from '../service/event.service';
import { EventDeleteDialogComponent } from '../delete/event-delete-dialog.component';
import { DataUtils } from 'app/core/util/data-util.service';
import { EventUpdateComponent } from '../update/event-update.component';
import { TipoEvento } from 'app/entities/enumerations/tipo-evento.model';
import { City } from 'app/entities/enumerations/city.model';
import { EventFormService } from '../update/event-form.service';

@Component({
  selector: 'jhi-event',
  templateUrl: './event.component.html',
  styleUrls: ['../event.css'],
})
export class EventComponent implements OnInit {
  events?: IEvent[];
  isLoading = false;
  active = 1;
  tipoEventoValues = Object.keys(TipoEvento);
  cityValues = Object.keys(City);
  predicate = 'id';
  ascending = true;
  clienteZonaHoraria?: string;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  fileUrl = '';
  constructor(
    protected eventService: EventService,
    protected eventFormService: EventFormService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected dataUtils: DataUtils,
    protected modalService: NgbModal
  ) {
    this.obtenerZonaHorariaCliente();
  }

  trackId = (_index: number, item: IEvent): number => this.eventService.getEventIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  create(): void {
    const modalRef = this.modalService.open(EventUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_SAVED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }
  obtenerZonaHorariaCliente() {
    this.clienteZonaHoraria = Intl.DateTimeFormat().resolvedOptions().timeZone;
  }
  update(event: IEvent): void {
    // const urlParts = ['/event', event.id.toString(), 'edit'];
    // const url = urlParts.join('/');
    // this.router.navigateByUrl(url);
    // this.router.navigate(['./'], {
    //   relativeTo: this.activatedRoute,
    //   queryParams: queryParamsObj,
    // }
    // const modalRef = this.modalService.open(EventUpdateComponent, { size: 'lg', backdrop: 'static' });
    // modalRef.componentInstance.eventItem = event;
    // modalRef.closed
    //   .pipe(
    //     filter(reason => reason === ITEM_SAVED_EVENT),
    //     switchMap(() => this.loadFromBackendWithRouteInformations())
    //   )
    //   .subscribe({
    //     next: (res: EntityArrayResponseType) => {
    //       this.onResponseSuccess(res);
    //     },
    //   });
  }
  delete(event: IEvent): void {
    const modalRef = this.modalService.open(EventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.event = event;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.events = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IEvent[] | null): IEvent[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(page?: number, predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.eventService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  login(): void {
    this.router.navigate(['/login']);
  }
}
