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
  picture: FormControl<ISong['picture']>;
  pictureContentType: FormControl<ISong['pictureContentType']>;
  duration: FormControl<ISong['duration']>;
  audio: FormControl<ISong['audio']>;
  audioContentType: FormControl<ISong['audioContentType']>;
  artists: FormControl<ISong['artists']>;
  musicGenre: FormControl<ISong['musicGenre']>;
  listDetails: FormControl<ISong['listDetails']>;
  album: FormControl<ISong['album']>;
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
      picture: new FormControl(songRawValue.picture),
      pictureContentType: new FormControl(songRawValue.pictureContentType),
      duration: new FormControl(songRawValue.duration),
      audio: new FormControl(songRawValue.audio),
      audioContentType: new FormControl(songRawValue.audioContentType),
      artists: new FormControl(songRawValue.artists),
      musicGenre: new FormControl(songRawValue.musicGenre),
      listDetails: new FormControl(songRawValue.listDetails),
      album: new FormControl(songRawValue.album),
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
