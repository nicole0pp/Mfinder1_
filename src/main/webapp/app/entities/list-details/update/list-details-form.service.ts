import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IListDetails, NewListDetails } from '../list-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IListDetails for edit and NewListDetailsFormGroupInput for create.
 */
type ListDetailsFormGroupInput = IListDetails | PartialWithRequiredKeyOf<NewListDetails>;

type ListDetailsFormDefaults = Pick<NewListDetails, 'id'>;

type ListDetailsFormGroupContent = {
  id: FormControl<IListDetails['id'] | NewListDetails['id']>;
};

export type ListDetailsFormGroup = FormGroup<ListDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ListDetailsFormService {
  createListDetailsFormGroup(listDetails: ListDetailsFormGroupInput = { id: null }): ListDetailsFormGroup {
    const listDetailsRawValue = {
      ...this.getFormDefaults(),
      ...listDetails,
    };
    return new FormGroup<ListDetailsFormGroupContent>({
      id: new FormControl(
        { value: listDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
    });
  }

  getListDetails(form: ListDetailsFormGroup): IListDetails | NewListDetails {
    if (form.controls.id.disabled) {
      // form.value returns id with null value for FormGroup with only one FormControl
      return { id: null };
    }
    return form.getRawValue() as IListDetails | NewListDetails;
  }

  resetForm(form: ListDetailsFormGroup, listDetails: ListDetailsFormGroupInput): void {
    const listDetailsRawValue = { ...this.getFormDefaults(), ...listDetails };
    form.reset(
      {
        ...listDetailsRawValue,
        id: { value: listDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ListDetailsFormDefaults {
    return {
      id: null,
    };
  }
}
