import { IFavoriteList, NewFavoriteList } from './favorite-list.model';

export const sampleWithRequiredData: IFavoriteList = {
  id: 75568,
  name: 'quantifying',
};

export const sampleWithPartialData: IFavoriteList = {
  id: 2190,
  name: 'Representante Granito',
};

export const sampleWithFullData: IFavoriteList = {
  id: 48707,
  name: 'Refinado colaboración',
  picture: '../fake-data/blob/hipster.png',
  pictureContentType: 'unknown',
};

export const sampleWithNewData: NewFavoriteList = {
  name: 'driver Producto Ensalada',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
