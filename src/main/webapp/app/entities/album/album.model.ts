import dayjs from 'dayjs/esm';
import { IArtist } from 'app/entities/artist/artist.model';

export interface IAlbum {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  publicationDate?: dayjs.Dayjs | null;
  atist?: Pick<IArtist, 'id'> | null;
}

export type NewAlbum = Omit<IAlbum, 'id'> & { id: null };
