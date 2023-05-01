import { IUser } from '../user/user.model';

export interface IArtist {
  id: number;
  user?: Pick<IUser, 'id'> | null;
  userLogin?: Pick<IUser, 'login'> | null;
  artistName?: string | null;
  insta_link?: string | null;
  spoti_link?: string | null;
}

export type NewArtist = Omit<IArtist, 'id'> & { id: null };
