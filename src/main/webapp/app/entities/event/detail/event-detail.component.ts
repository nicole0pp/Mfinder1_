import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';

import { IEvent } from '../event.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EventDeleteDialogComponent } from '../delete/event-delete-dialog.component';
import { ASC, DEFAULT_SORT_DATA, DESC, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { Observable, combineLatest, filter, switchMap, tap } from 'rxjs';
import { EntityArrayResponseType, EventService } from '../service/event.service';
import { City } from 'app/entities/enumerations/city.model';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { Account } from 'app/core/auth/account.model';
import { HttpHeaders } from '@angular/common/http';
import dayjs, { Dayjs } from 'dayjs';
import { TipoEvento } from 'app/entities/enumerations/tipo-evento.model';

@Component({
  selector: 'jhi-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['../event.css'],
})
export class EventDetailComponent implements OnInit {
  events?: IEvent[];
  event: IEvent | null = null;

  isLoading = false;
  active = 1;
  tipoEventoValues = Object.keys(TipoEvento);
  cityValues = Object.keys(City);
  predicate = 'id';
  ascending = true;
  clienteZonaHoraria?: string;
  selectedCity?: string;
  currentAccount: Account | null = null;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;
  fileUrl = '';

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    protected eventService: EventService,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  delete(event: IEvent): void {
    const modalRef = this.modalService.open(EventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.event = event;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.pipe(
      filter(reason => reason === ITEM_DELETED_EVENT),
      switchMap(() => this.router.navigate(['/event']))
    );
  }
  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.events = dataFromBody;
    this.events?.sort((a, b) => (dayjs(b.startDate).isBefore(a.startDate) ? -1 : 1));
  }

  protected fillComponentAttributesFromResponseBody(data: IEvent[] | null): IEvent[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
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
  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  esFechaPosterior(fechaEndDate?: Dayjs | null): boolean {
    const fechaActual = new Date();
    if (fechaEndDate) {
      return fechaEndDate.toDate() < fechaActual;
    } else {
      return false;
    }
  }
}
