import dayjs from 'dayjs/esm';

import { IAlbum, NewAlbum } from './album.model';

export const sampleWithRequiredData: IAlbum = {
  id: 51589,
  name: 'back-end',
};

export const sampleWithPartialData: IAlbum = {
  id: 99693,
  name: 'backing Mesa deliver',
};

export const sampleWithFullData: IAlbum = {
  id: 34293,
  name: 'SQL',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
  publicationDate: dayjs('2023-01-27'),
};

export const sampleWithNewData: NewAlbum = {
  name: 'Corporativo Zapatos',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
