import { IEvent } from '../event/event.model';
import { IUser } from '../user/user.model';

export interface IRatingEvent {
  id: number;
  comment?: string | null;
  rating?: number | null;
  event?: Pick<IEvent, 'id'> | null;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  image?: string | null;
  imageContentType?: string | null;
}

export type NewRatingEvent = Omit<IRatingEvent, 'id'> & { id: null };
