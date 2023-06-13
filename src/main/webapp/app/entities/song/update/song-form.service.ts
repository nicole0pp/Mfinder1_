import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISong, NewSong } from '../song.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISong for edit and NewSongFormGroupInput for create.
 */
type SongFormGroupInput = ISong | PartialWithRequiredKeyOf<NewSong>;

type SongFormDefaults = Pick<NewSong, 'id'>;

type SongFormGroupContent = {
  id: FormControl<ISong['id'] | NewSong['id']>;
  name: FormControl<ISong['name']>;
  audio: FormControl<ISong['audio']>;
  audioContentType: FormControl<ISong['audioContentType']>;
  duration: FormControl<ISong['duration']>;
  musicGenre: FormControl<ISong['musicGenre']>;
};

export type SongFormGroup = FormGroup<SongFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SongFormService {
  createSongFormGroup(song: SongFormGroupInput = { id: null }): SongFormGroup {
    const songRawValue = {
      ...this.getFormDefaults(),
      ...song,
    };
    return new FormGroup<SongFormGroupContent>({
      id: new FormControl(
        { value: songRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(songRawValue.name, {
        validators: [Validators.required],
      }),
      audio: new FormControl(songRawValue.audio, {
        validators: [Validators.required],
      }),
      audioContentType: new FormControl(songRawValue.audioContentType),
      duration: new FormControl(songRawValue.duration),
      musicGenre: new FormControl(songRawValue.musicGenre),
    });
  }

  getSong(form: SongFormGroup): ISong | NewSong {
    return form.getRawValue() as ISong | NewSong;
  }

  resetForm(form: SongFormGroup, song: SongFormGroupInput): void {
    const songRawValue = { ...this.getFormDefaults(), ...song };
    form.reset(
      {
        ...songRawValue,
        id: { value: songRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SongFormDefaults {
    return {
      id: null,
    };
  }
}
