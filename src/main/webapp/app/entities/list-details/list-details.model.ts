import { IFavoriteList } from '../favorite-list/favorite-list.model';
import { ISong } from '../song/song.model';

export interface IListDetails {
  id: number;
  list?: Pick<IFavoriteList, 'id'> | null;
  song?: Pick<ISong, 'id'> | null;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export type NewListDetails = Omit<IListDetails, 'id'> & { id: null };
