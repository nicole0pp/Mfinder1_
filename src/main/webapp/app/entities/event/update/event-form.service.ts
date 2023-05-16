import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvent, NewEvent } from '../event.model';
import dayjs from 'dayjs/esm';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type EventFormGroupInput = IEvent | PartialWithRequiredKeyOf<NewEvent>;

type EventFormDefaults = Pick<NewEvent, 'id'>;

type EventFormGroupContent = {
  id: FormControl<IEvent['id'] | NewEvent['id']>;
  name: FormControl<IEvent['name']>;
  image: FormControl<IEvent['image']>;
  imageContentType: FormControl<IEvent['imageContentType']>;
  tipoEvento: FormControl<IEvent['tipoEvento']>;
  startDate: FormControl<IEvent['startDate']>;
  endDate: FormControl<IEvent['endDate']>;
  startTime: FormControl<string | null>;
  endTime: FormControl<string | null>;
  location: FormControl<IEvent['location']>;
  city: FormControl<IEvent['city']>;
  description: FormControl<IEvent['description']>;
  seatCapacity: FormControl<IEvent['seatCapacity']>;
  artists: FormControl<IEvent['artists']>;
  ratings: FormControl<IEvent['ratings']>;
};

export type EventFormGroup = FormGroup<EventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormService {
  createEventFormGroup(event: EventFormGroupInput = { id: null }): EventFormGroup {
    const eventRawValue = {
      ...this.getFormDefaults(),
      ...event,
    };
    return new FormGroup<EventFormGroupContent>({
      id: new FormControl(
        { value: eventRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(eventRawValue.name, {
        validators: [Validators.required],
      }),
      image: new FormControl(eventRawValue.image),
      imageContentType: new FormControl(eventRawValue.imageContentType),
      tipoEvento: new FormControl(eventRawValue.tipoEvento, {
        validators: [Validators.required],
      }),
      startDate: new FormControl(eventRawValue.startDate, {
        validators: [Validators.required],
      }),
      endDate: new FormControl(eventRawValue.endDate, {
        validators: [Validators.required],
      }),
      startTime: new FormControl('', {
        validators: [Validators.required],
      }),
      endTime: new FormControl('', {
        validators: [Validators.required],
      }),
      location: new FormControl(eventRawValue.location),
      city: new FormControl(eventRawValue.city),
      description: new FormControl(eventRawValue.description, {
        validators: Validators.maxLength(254),
      }),
      seatCapacity: new FormControl(eventRawValue.seatCapacity, {
        validators: [Validators.min(2), Validators.required],
      }),
      artists: new FormControl(eventRawValue.artists),
      ratings: new FormControl(eventRawValue.ratings),
    });
  }

  getEvent(form: EventFormGroup): IEvent | NewEvent {
    return form.getRawValue() as IEvent | NewEvent;
  }

  resetForm(form: EventFormGroup, event: EventFormGroupInput): void {
    const eventRawValue = { ...this.getFormDefaults(), ...event };
    form.reset(
      {
        ...eventRawValue,
        id: { value: eventRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormDefaults {
    return {
      id: null,
    };
  }
}
