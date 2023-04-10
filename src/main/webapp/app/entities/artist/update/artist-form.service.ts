import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IArtist, NewArtist } from '../artist.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IArtist for edit and NewArtistFormGroupInput for create.
 */
type ArtistFormGroupInput = IArtist | PartialWithRequiredKeyOf<NewArtist>;

type ArtistFormDefaults = Pick<NewArtist, 'id'>;

type ArtistFormGroupContent = {
  id: FormControl<IArtist['id'] | NewArtist['id']>;
  user: FormControl<IArtist['user']>;
  artistName: FormControl<IArtist['artistName']>;
  insta_link: FormControl<IArtist['insta_link']>;
  spoti_link: FormControl<IArtist['spoti_link']>;
};

export type ArtistFormGroup = FormGroup<ArtistFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ArtistFormService {
  createArtistFormGroup(artist: ArtistFormGroupInput = { id: null }): ArtistFormGroup {
    const artistRawValue = {
      ...this.getFormDefaults(),
      ...artist,
    };
    return new FormGroup<ArtistFormGroupContent>({
      id: new FormControl(
        { value: artistRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      user: new FormControl(artistRawValue.user),
      artistName: new FormControl(artistRawValue.artistName),
      insta_link: new FormControl(artistRawValue.insta_link),
      spoti_link: new FormControl(artistRawValue.spoti_link),
    });
  }

  getArtist(form: ArtistFormGroup): IArtist | NewArtist {
    if (form.controls.id.disabled) {
      // form.value returns id with null value for FormGroup with only one FormControl
      return { id: null };
    }
    return form.getRawValue() as IArtist | NewArtist;
  }

  resetForm(form: ArtistFormGroup, artist: ArtistFormGroupInput): void {
    const artistRawValue = { ...this.getFormDefaults(), ...artist };
    form.reset(
      {
        ...artistRawValue,
        id: { value: artistRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ArtistFormDefaults {
    return {
      id: null,
    };
  }
}
