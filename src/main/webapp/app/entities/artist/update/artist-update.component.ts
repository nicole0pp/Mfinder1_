import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ArtistFormService, ArtistFormGroup } from './artist-form.service';
import { IArtist } from '../artist.model';
import { ArtistService } from '../service/artist.service';
import { UserService } from 'app/entities/user/user.service';
import { IUser } from 'app/admin/user-management/user-management.model';

@Component({
  selector: 'jhi-artist-update',
  templateUrl: './artist-update.component.html',
})
export class ArtistUpdateComponent implements OnInit {
  isSaving = false;
  artist: IArtist | null = null;
  userSharedCollecion: IUser[] = [];

  editForm: ArtistFormGroup = this.artistFormService.createArtistFormGroup();

  constructor(
    protected artistService: ArtistService,
    protected artistFormService: ArtistFormService,
    protected activatedRoute: ActivatedRoute,
    protected userService: UserService
  ) {}
  compareUser = (o1: IUser, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ artist }) => {
      this.artist = artist;
      if (artist) {
        this.updateForm(artist);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const artist = this.artistFormService.getArtist(this.editForm);
    if (artist.id !== null) {
      this.subscribeToSaveResponse(this.artistService.update(artist));
    } else {
      this.subscribeToSaveResponse(this.artistService.create(artist));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArtist>>): void {
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

  protected updateForm(artist: IArtist): void {
    this.artist = artist;
    this.artistFormService.resetForm(this.editForm, artist);

    this.userSharedCollecion = this.userService.addUserToCollectionIfMissing<IUser>(this.userSharedCollecion, artist.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.artist?.user)))
      .subscribe((users: IUser[]) => (this.userSharedCollecion = users));
  }
}
