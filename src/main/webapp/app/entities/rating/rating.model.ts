import { IUser } from '../user/user.model';

export interface IRating {
  id: number;
  comment?: string | null;
  rating?: number | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewRating = Omit<IRating, 'id'> & { id: null };
