import { IListDetails, NewListDetails } from './list-details.model';

export const sampleWithRequiredData: IListDetails = {
  id: 26352,
};

export const sampleWithPartialData: IListDetails = {
  id: 25985,
};

export const sampleWithFullData: IListDetails = {
  id: 29170,
};

export const sampleWithNewData: NewListDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
