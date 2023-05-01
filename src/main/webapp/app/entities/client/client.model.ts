import { IUser } from '../user/user.model';

export interface IClient {
  id: number;
  user?: Pick<IUser, 'id'> | null;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
