import { ISong } from '../song/song.model';

export interface IRatingSong {
  id: number;
  comment?: string | null;
  rating?: number | null;
  song?: Pick<ISong, 'id'> | null;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export type NewRatingSong = Omit<IRatingSong, 'id'> & { id: null };
