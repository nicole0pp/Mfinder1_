import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRatingEvent, NewRatingEvent } from '../rating-event.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRating for edit and NewRatingFormGroupInput for create.
 */
type RatingEventFormGroupInput = IRatingEvent | PartialWithRequiredKeyOf<NewRatingEvent>;

type RatingEventFormDefaults = Pick<NewRatingEvent, 'id'>;

type RatingEventFormGroupContent = {
  id: FormControl<IRatingEvent['id'] | NewRatingEvent['id']>;
  comment: FormControl<IRatingEvent['comment']>;
  rating: FormControl<IRatingEvent['rating']>;
  event: FormControl<IRatingEvent['event']>;
};

export type RatingEventFormGroup = FormGroup<RatingEventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RatingEventFormService {
  createRatingEventFormGroup(ratingEvent: RatingEventFormGroupInput = { id: null }): RatingEventFormGroup {
    const ratingEventRawValue = {
      ...this.getFormDefaults(),
      ...ratingEvent,
    };
    return new FormGroup<RatingEventFormGroupContent>({
      id: new FormControl(
        { value: ratingEventRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      comment: new FormControl(ratingEventRawValue.comment),
      rating: new FormControl(ratingEventRawValue.rating, {
        validators: [Validators.required, Validators.min(0), Validators.max(10)],
      }),
      event: new FormControl(ratingEventRawValue.event),
    });
  }

  getRating(form: RatingEventFormGroup): IRatingEvent | NewRatingEvent {
    return form.getRawValue() as IRatingEvent | NewRatingEvent;
  }

  resetForm(form: RatingEventFormGroup, ratingEvent: RatingEventFormGroupInput): void {
    const ratingEventRawValue = { ...this.getFormDefaults(), ...ratingEvent };
    form.reset(
      {
        ...ratingEventRawValue,
        id: { value: ratingEventRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RatingEventFormDefaults {
    return {
      id: null,
    };
  }
}
