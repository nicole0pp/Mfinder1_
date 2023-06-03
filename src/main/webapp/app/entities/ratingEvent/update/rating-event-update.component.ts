import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RatingEventFormService, RatingEventFormGroup } from './rating-event-form.service';
import { IRatingEvent } from '../rating-event.model';
import { RatingEventService } from '../service/rating-event.service';

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-event-update.component.html',
})
export class RatingEventUpdateComponent implements OnInit {
  isSaving = false;
  ratingEvent: IRatingEvent | null = null;

  editForm: RatingEventFormGroup = this.ratingEventFormService.createRatingEventFormGroup();

  constructor(
    protected ratingEventService: RatingEventService,
    protected ratingEventFormService: RatingEventFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratingEvent }) => {
      this.ratingEvent = ratingEvent;
      if (ratingEvent) {
        this.updateForm(ratingEvent);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ratingEvent = this.ratingEventFormService.getRating(this.editForm);
    if (ratingEvent.id !== null) {
      this.subscribeToSaveResponse(this.ratingEventService.update(ratingEvent));
    } else {
      this.subscribeToSaveResponse(this.ratingEventService.create(ratingEvent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRatingEvent>>): void {
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

  protected updateForm(ratingEvent: IRatingEvent): void {
    this.ratingEvent = ratingEvent;
    this.ratingEventFormService.resetForm(this.editForm, ratingEvent);
  }
}
