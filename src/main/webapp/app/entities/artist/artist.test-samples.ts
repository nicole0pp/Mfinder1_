import { IArtist, NewArtist } from './artist.model';

export const sampleWithRequiredData: IArtist = {
  id: 70921,
};

export const sampleWithPartialData: IArtist = {
  id: 78132,
  insta_link: 'Cantabria Parcela Borders',
  spoti_link: 'incubate',
};

export const sampleWithFullData: IArtist = {
  id: 15109,
  insta_link: 'driver Operaciones Avon',
  spoti_link: 'Savings Acero e-commerce',
};

export const sampleWithNewData: NewArtist = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
