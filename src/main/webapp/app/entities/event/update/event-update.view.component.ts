import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EventFormService, EventFormGroup } from './event-form.service';
import { IEvent } from '../event.model';
import { EventService } from '../service/event.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { TipoEvento } from 'app/entities/enumerations/tipo-evento.model';
import { IArtist } from 'app/entities/artist/artist.model';
import { ArtistService } from 'app/entities/artist/service/artist.service';
import { ITEM_SAVED_EVENT } from 'app/config/navigation.constants';
import { City } from 'app/entities/enumerations/city.model';
import dayjs from 'dayjs/esm';
import { IUser } from 'app/admin/user-management/user-management.model';

@Component({
  selector: 'jhi-event-update.view',
  templateUrl: './event-update.view.component.html',
})
export class EventUpdateViewComponent implements OnInit {
  isSaving = false;
  event: IEvent | null = null;
  userSharedCollecion: IUser[] = [];
  tipoEventoValues = Object.keys(TipoEvento);
  cityValues = Object.keys(City);
  artists: string[] = [];

  editForm: EventFormGroup = this.eventFormService.createEventFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    private route: ActivatedRoute,
    protected eventManager: EventManager,
    protected eventService: EventService,
    protected eventFormService: EventFormService,
    protected artistService: ArtistService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareArtist = (o1: IArtist | null, o2: IArtist | null): boolean => this.artistService.compareArtist(o1, o2);

  ngOnInit(): void {
    this.route.data.subscribe(({ event }) => {
      if (event) {
        this.editForm.reset(event);
      } else {
        this.editForm.reset(event.newEvent);
      }
    });
    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
      if (event) {
        this.updateForm(event);
      }
      this.eventService.query();
      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('mFinder1App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }
  save(): void {
    this.isSaving = true;
    dayjs.locale('es');
    const startTime = Object(this.editForm.get('startTime')?.value);
    var stringStartTime = '';
    if (startTime != null) {
      for (const clave in startTime) {
        if (startTime.hasOwnProperty(clave)) {
          const valor = startTime[clave];
          stringStartTime += `${valor}: `;
        }
      }
      stringStartTime = stringStartTime.slice(0, -2);
    }
    const endTime = Object(this.editForm.get('endTime')?.value);
    var stringEndTime = '';
    if (endTime != null) {
      for (const clave in endTime) {
        if (endTime.hasOwnProperty(clave)) {
          const valor = endTime[clave];
          stringEndTime += `${valor}: `;
        }
      }
      stringEndTime = stringEndTime.slice(0, -2);
    }
    const event = this.eventFormService.getEvent(this.editForm);
    if (event.id !== null) {
      this.eventService.update(event).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
      // this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event, stringStartTime, stringEndTime));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.isSaving = false;
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(event: IEvent): void {
    this.event = event;
    this.eventFormService.resetForm(this.editForm, event);
  }

  protected loadRelationshipsOptions(): void {
    this.artistService.artists().subscribe(artists => (this.artists = artists));
  }
}
