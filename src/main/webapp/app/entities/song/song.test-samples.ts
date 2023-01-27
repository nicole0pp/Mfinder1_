import { MusicGenre } from 'app/entities/enumerations/music-genre.model';

import { ISong, NewSong } from './song.model';

export const sampleWithRequiredData: ISong = {
  id: 99772,
  name: 'Bebes',
};

export const sampleWithPartialData: ISong = {
  id: 18279,
  name: 'invoice',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  audio: '../fake-data/blob/hipster.png',
  audioContentType: 'unknown',
  musicGenre: MusicGenre['REGGAETON'],
};

export const sampleWithFullData: ISong = {
  id: 41348,
  name: 'Joyería Visionario applications',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  duration: '31009',
  audio: '../fake-data/blob/hipster.png',
  audioContentType: 'unknown',
  artists: 'Regional',
  musicGenre: MusicGenre['KPOP'],
};

export const sampleWithNewData: NewSong = {
  name: 'Ingeniería de',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
