import { IArtist, NewArtist } from './artist.model';

export const sampleWithRequiredData: IArtist = {
  id: 70921,
};

export const sampleWithPartialData: IArtist = {
  id: 86525,
};

export const sampleWithFullData: IArtist = {
  id: 84326,
};

export const sampleWithNewData: NewArtist = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
