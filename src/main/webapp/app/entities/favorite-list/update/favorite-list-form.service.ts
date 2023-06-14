import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFavoriteList, NewFavoriteList } from '../favorite-list.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFavoriteList for edit and NewFavoriteListFormGroupInput for create.
 */
type FavoriteListFormGroupInput = IFavoriteList | PartialWithRequiredKeyOf<NewFavoriteList>;

type FavoriteListFormDefaults = Pick<NewFavoriteList, 'id'>;

type FavoriteListFormGroupContent = {
  id: FormControl<IFavoriteList['id'] | NewFavoriteList['id']>;
  name: FormControl<IFavoriteList['name']>;
  picture: FormControl<IFavoriteList['picture']>;
  pictureContentType: FormControl<IFavoriteList['pictureContentType']>;
};

export type FavoriteListFormGroup = FormGroup<FavoriteListFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FavoriteListFormService {
  createFavoriteListFormGroup(favoriteList: FavoriteListFormGroupInput = { id: null }): FavoriteListFormGroup {
    const favoriteListRawValue = {
      ...this.getFormDefaults(),
      ...favoriteList,
    };
    return new FormGroup<FavoriteListFormGroupContent>({
      id: new FormControl(
        { value: favoriteListRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(favoriteListRawValue.name, {
        validators: [Validators.required],
      }),
      picture: new FormControl(favoriteListRawValue.picture),
      pictureContentType: new FormControl(favoriteListRawValue.pictureContentType),
    });
  }

  getFavoriteList(form: FavoriteListFormGroup): IFavoriteList | NewFavoriteList {
    return form.getRawValue() as IFavoriteList | NewFavoriteList;
  }

  resetForm(form: FavoriteListFormGroup, favoriteList: FavoriteListFormGroupInput): void {
    const favoriteListRawValue = { ...this.getFormDefaults(), ...favoriteList };
    form.reset(
      {
        ...favoriteListRawValue,
        id: { value: favoriteListRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FavoriteListFormDefaults {
    return {
      id: null,
    };
  }
}
