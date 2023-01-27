import { IListDetails } from 'app/entities/list-details/list-details.model';
import { IAlbum } from 'app/entities/album/album.model';
import { MusicGenre } from 'app/entities/enumerations/music-genre.model';

export interface ISong {
  id: number;
  name?: string | null;
  picture?: string | null;
  pictureContentType?: string | null;
  duration?: string | null;
  audio?: string | null;
  audioContentType?: string | null;
  artists?: string | null;
  musicGenre?: MusicGenre | null;
  listDetails?: Pick<IListDetails, 'id'> | null;
  album?: Pick<IAlbum, 'id'> | null;
}

export type NewSong = Omit<ISong, 'id'> & { id: null };
