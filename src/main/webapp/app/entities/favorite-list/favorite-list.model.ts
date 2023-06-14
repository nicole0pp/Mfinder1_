import { IListDetails } from 'app/entities/list-details/list-details.model';

export interface IFavoriteList {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  listDetails?: Pick<IListDetails, 'id'> | null;
}

export type NewFavoriteList = Omit<IFavoriteList, 'id'> & { id: null };
