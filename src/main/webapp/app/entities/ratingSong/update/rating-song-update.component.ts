import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RatingSongFormService, RatingSongFormGroup } from './rating-song-form.service';
import { IRatingSong } from '../rating-song.model';
import { RatingSongService } from '../service/rating-song.service';

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-song-update.component.html',
})
export class RatingSongUpdateComponent implements OnInit {
  isSaving = false;
  ratingSong: IRatingSong | null = null;

  editForm: RatingSongFormGroup = this.ratingSongFormService.createRatingSongFormGroup();

  constructor(
    protected ratingSongService: RatingSongService,
    protected ratingSongFormService: RatingSongFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratingSong }) => {
      this.ratingSong = ratingSong;
      if (ratingSong) {
        this.updateForm(ratingSong);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ratingSong = this.ratingSongFormService.getRating(this.editForm);
    if (ratingSong.id !== null) {
      this.subscribeToSaveResponse(this.ratingSongService.update(ratingSong));
    } else {
      this.subscribeToSaveResponse(this.ratingSongService.create(ratingSong));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRatingSong>>): void {
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

  protected updateForm(ratingSong: IRatingSong): void {
    this.ratingSong = ratingSong;
    this.ratingSongFormService.resetForm(this.editForm, ratingSong);
  }
}
