import { IUser } from '../user/user.model';

export interface IArtist {
  id: number;
  user?: Pick<IUser, 'id' | 'login'> | null;
  image?: string | null;
  imageProfileContentType?: string | null;
  artistName?: string | null;
  insta_link?: string | null;
  spoti_link?: string | null;
}

export type NewArtist = Omit<IArtist, 'id'> & { id: null };
