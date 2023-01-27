import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { FavoriteListFormService, FavoriteListFormGroup } from './favorite-list-form.service';
import { IFavoriteList } from '../favorite-list.model';
import { FavoriteListService } from '../service/favorite-list.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IListDetails } from 'app/entities/list-details/list-details.model';
import { ListDetailsService } from 'app/entities/list-details/service/list-details.service';
import { IArtist } from 'app/entities/artist/artist.model';
import { ArtistService } from 'app/entities/artist/service/artist.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

@Component({
  selector: 'jhi-favorite-list-update',
  templateUrl: './favorite-list-update.component.html',
})
export class FavoriteListUpdateComponent implements OnInit {
  isSaving = false;
  favoriteList: IFavoriteList | null = null;

  listDetailsSharedCollection: IListDetails[] = [];
  artistsSharedCollection: IArtist[] = [];
  clientsSharedCollection: IClient[] = [];

  editForm: FavoriteListFormGroup = this.favoriteListFormService.createFavoriteListFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected favoriteListService: FavoriteListService,
    protected favoriteListFormService: FavoriteListFormService,
    protected listDetailsService: ListDetailsService,
    protected artistService: ArtistService,
    protected clientService: ClientService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareListDetails = (o1: IListDetails | null, o2: IListDetails | null): boolean => this.listDetailsService.compareListDetails(o1, o2);

  compareArtist = (o1: IArtist | null, o2: IArtist | null): boolean => this.artistService.compareArtist(o1, o2);

  compareClient = (o1: IClient | null, o2: IClient | null): boolean => this.clientService.compareClient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoriteList }) => {
      this.favoriteList = favoriteList;
      if (favoriteList) {
        this.updateForm(favoriteList);
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
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const favoriteList = this.favoriteListFormService.getFavoriteList(this.editForm);
    if (favoriteList.id !== null) {
      this.subscribeToSaveResponse(this.favoriteListService.update(favoriteList));
    } else {
      this.subscribeToSaveResponse(this.favoriteListService.create(favoriteList));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFavoriteList>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(favoriteList: IFavoriteList): void {
    this.favoriteList = favoriteList;
    this.favoriteListFormService.resetForm(this.editForm, favoriteList);

    this.listDetailsSharedCollection = this.listDetailsService.addListDetailsToCollectionIfMissing<IListDetails>(
      this.listDetailsSharedCollection,
      favoriteList.listDetails
    );
    this.artistsSharedCollection = this.artistService.addArtistToCollectionIfMissing<IArtist>(
      this.artistsSharedCollection,
      favoriteList.artist
    );
    this.clientsSharedCollection = this.clientService.addClientToCollectionIfMissing<IClient>(
      this.clientsSharedCollection,
      favoriteList.client
    );
  }

  protected loadRelationshipsOptions(): void {
    this.listDetailsService
      .query()
      .pipe(map((res: HttpResponse<IListDetails[]>) => res.body ?? []))
      .pipe(
        map((listDetails: IListDetails[]) =>
          this.listDetailsService.addListDetailsToCollectionIfMissing<IListDetails>(listDetails, this.favoriteList?.listDetails)
        )
      )
      .subscribe((listDetails: IListDetails[]) => (this.listDetailsSharedCollection = listDetails));

    this.artistService
      .query()
      .pipe(map((res: HttpResponse<IArtist[]>) => res.body ?? []))
      .pipe(map((artists: IArtist[]) => this.artistService.addArtistToCollectionIfMissing<IArtist>(artists, this.favoriteList?.artist)))
      .subscribe((artists: IArtist[]) => (this.artistsSharedCollection = artists));

    this.clientService
      .query()
      .pipe(map((res: HttpResponse<IClient[]>) => res.body ?? []))
      .pipe(map((clients: IClient[]) => this.clientService.addClientToCollectionIfMissing<IClient>(clients, this.favoriteList?.client)))
      .subscribe((clients: IClient[]) => (this.clientsSharedCollection = clients));
  }
}
