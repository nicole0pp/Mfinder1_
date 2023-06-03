import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRatingSong, NewRatingSong } from '../rating-song.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRating for edit and NewRatingFormGroupInput for create.
 */
type RatingSongFormGroupInput = IRatingSong | PartialWithRequiredKeyOf<NewRatingSong>;

type RatingSongFormDefaults = Pick<NewRatingSong, 'id'>;

type RatingSongFormGroupContent = {
  id: FormControl<IRatingSong['id'] | NewRatingSong['id']>;
  comment: FormControl<IRatingSong['comment']>;
  rating: FormControl<IRatingSong['rating']>;
  song: FormControl<IRatingSong['song']>;
};

export type RatingSongFormGroup = FormGroup<RatingSongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RatingSongFormService {
  createRatingSongFormGroup(ratingSong: RatingSongFormGroupInput = { id: null }): RatingSongFormGroup {
    const ratingSongRawValue = {
      ...this.getFormDefaults(),
      ...ratingSong,
    };
    return new FormGroup<RatingSongFormGroupContent>({
      id: new FormControl(
        { value: ratingSongRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      comment: new FormControl(ratingSongRawValue.comment),
      rating: new FormControl(ratingSongRawValue.rating, {
        validators: [Validators.required, Validators.min(0), Validators.max(10)],
      }),
      song: new FormControl(ratingSongRawValue.song),
    });
  }

  getRating(form: RatingSongFormGroup): IRatingSong | NewRatingSong {
    return form.getRawValue() as IRatingSong | NewRatingSong;
  }

  resetForm(form: RatingSongFormGroup, ratingSong: RatingSongFormGroupInput): void {
    const ratingSongRawValue = { ...this.getFormDefaults(), ...ratingSong };
    form.reset(
      {
        ...ratingSongRawValue,
        id: { value: ratingSongRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RatingSongFormDefaults {
    return {
      id: null,
    };
  }
}
