import { IListDetails } from 'app/entities/list-details/list-details.model';
import { IAlbum } from 'app/entities/album/album.model';
import { MusicGenre } from 'app/entities/enumerations/music-genre.model';
import { Duration } from 'dayjs/esm/plugin/duration';
import { IArtist } from '../artist/artist.model';

export interface ISong {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  duration?: number | null;
  audio?: string | null;
  audioContentType?: string | null;
  artist?: Pick<IArtist, 'id'> | null;
  musicGenre?: MusicGenre | null;
  listDetails?: Pick<IListDetails, 'id'> | null;
  album?: Pick<IAlbum, 'id'> | null;
}

export type NewSong = Omit<ISong, 'id'> & { id: null };
