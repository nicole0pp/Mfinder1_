import { IListDetails } from 'app/entities/list-details/list-details.model';
import { IArtist } from 'app/entities/artist/artist.model';
import { IClient } from 'app/entities/client/client.model';

export interface IFavoriteList {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  listDetails?: Pick<IListDetails, 'id'> | null;
  artist?: Pick<IArtist, 'id'> | null;
  client?: Pick<IClient, 'id'> | null;
}

export type NewFavoriteList = Omit<IFavoriteList, 'id'> & { id: null };
