import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEvent, NewEvent } from '../event.model';
import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type EventFormGroupInput = IEvent | PartialWithRequiredKeyOf<NewEvent>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEvent | NewEvent> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type EventFormRawValue = FormValueOf<IEvent>;

type NewEventFormRawValue = FormValueOf<NewEvent>;

type EventFormDefaults = Pick<NewEvent, 'id' | 'startDate' | 'endDate' | 'artists'>;

type EventFormGroupContent = {
  id: FormControl<EventFormRawValue['id'] | NewEvent['id']>;
  name: FormControl<EventFormRawValue['name']>;
  image: FormControl<EventFormRawValue['image']>;
  imageContentType: FormControl<EventFormRawValue['imageContentType']>;
  tipoEvento: FormControl<EventFormRawValue['tipoEvento']>;
  startDate: FormControl<EventFormRawValue['startDate']>;
  endDate: FormControl<EventFormRawValue['endDate']>;
  location: FormControl<EventFormRawValue['location']>;
  city: FormControl<EventFormRawValue['city']>;
  description: FormControl<EventFormRawValue['description']>;
  seatCapacity: FormControl<EventFormRawValue['seatCapacity']>;
  artists: FormControl<EventFormRawValue['artists']>;
  ratings: FormControl<EventFormRawValue['ratings']>;
};

export type EventFormGroup = FormGroup<EventFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EventFormService {
  createEventFormGroup(event: EventFormGroupInput = { id: null }): EventFormGroup {
    const eventRawValue = this.convertEventToEventRawValue({
      ...this.getFormDefaults(),
      ...event,
    });
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
      image: new FormControl(eventRawValue.image, {
        validators: [Validators.required],
      }),
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
      location: new FormControl(eventRawValue.location),
      city: new FormControl(eventRawValue.city, {
        validators: [Validators.required],
      }),
      description: new FormControl(eventRawValue.description, {
        validators: Validators.maxLength(254),
      }),
      seatCapacity: new FormControl(eventRawValue.seatCapacity, {
        validators: [Validators.min(2), Validators.required],
      }),
      artists: new FormControl(eventRawValue.artists ?? []),
      ratings: new FormControl(eventRawValue.ratings),
    });
  }

  getEvent(form: EventFormGroup): IEvent | NewEvent {
    return this.convertEventRawValueToEvent(form.getRawValue() as EventFormRawValue | NewEventFormRawValue);
  }

  resetForm(form: EventFormGroup, event: EventFormGroupInput): void {
    const eventRawValue = this.convertEventToEventRawValue({ ...this.getFormDefaults(), ...event });
    form.reset(
      {
        ...eventRawValue,
        id: { value: eventRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EventFormDefaults {
    const currentTime = dayjs();
    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      artists: [],
    };
  }

  private convertEventRawValueToEvent(rawEvent: EventFormRawValue | NewEventFormRawValue): IEvent | NewEvent {
    return {
      ...rawEvent,
      startDate: dayjs(rawEvent.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawEvent.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertEventToEventRawValue(
    event: IEvent | (Partial<NewEvent> & EventFormDefaults)
  ): EventFormRawValue | PartialWithRequiredKeyOf<NewEventFormRawValue> {
    return {
      ...event,
      startDate: event.startDate ? event.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: event.endDate ? event.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
