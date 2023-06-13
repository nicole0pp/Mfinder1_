import dayjs from 'dayjs/esm';
import { IArtist } from 'app/entities/artist/artist.model';
import { ISong } from '../song/song.model';

export interface IAlbum {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  publicationDate?: Date;
  artist?: Pick<IArtist, 'id' | 'artistName' | 'image' | 'imageProfileContentType'> | null;
  songs?: Pick<ISong, 'id'>[] | null;
}

export type NewAlbum = Omit<IAlbum, 'id'> & { id: null };
