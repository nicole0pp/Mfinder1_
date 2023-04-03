import { IRating, NewRating } from './rating.model';

export const sampleWithRequiredData: IRating = {
  id: 92458,
  rating: 9,
};

export const sampleWithPartialData: IRating = {
  id: 62920,
  rating: 5,
};

export const sampleWithFullData: IRating = {
  id: 33071,
  comment: 'Operaciones',
  rating: 7,
};

export const sampleWithNewData: NewRating = {
  rating: 10,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
