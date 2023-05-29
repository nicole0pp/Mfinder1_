import { Component, Input, OnInit } from '@angular/core';
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
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEM_SAVED_EVENT } from 'app/config/navigation.constants';
import { City } from 'app/entities/enumerations/city.model';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html',
})
export class EventUpdateComponent implements OnInit {
  isSaving = false;
  event: IEvent | null = null;

  tipoEventoValues = Object.keys(TipoEvento);
  cityValues = Object.keys(City);
  artistsSharedCollection: IArtist[] = [];

  editForm: EventFormGroup = this.eventFormService.createEventFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected eventService: EventService,
    protected eventFormService: EventFormService,
    protected artistService: ArtistService,
    protected activeModal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareArtist = (o1: IArtist | null, o2: IArtist | null): boolean => this.artistService.compareArtist(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      this.event = event;
      if (event) {
        this.updateForm(event);
      }

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
    // window.history.back();
    this.activeModal.dismiss();
  }
  save(): void {
    this.isSaving = true;
    const event = this.eventFormService.getEvent(this.editForm);
    if (event.id !== null) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
    this.activeModal.close(ITEM_SAVED_EVENT);
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(event: IEvent): void {
    this.event = event;
    this.eventFormService.resetForm(this.editForm, event);

    this.artistsSharedCollection = this.artistService.addArtistToCollectionIfMissing<IArtist>(
      this.artistsSharedCollection,
      ...(event.artists ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.artistService
      .query()
      .pipe(map((res: HttpResponse<IArtist[]>) => res.body ?? []))
      .pipe(
        map((artists: IArtist[]) => this.artistService.addArtistToCollectionIfMissing<IArtist>(artists, ...(this.event?.artists ?? [])))
      )
      .subscribe((artists: IArtist[]) => (this.artistsSharedCollection = artists));
  }
  cancel(): void {
    this.activeModal.dismiss();
  }
}
