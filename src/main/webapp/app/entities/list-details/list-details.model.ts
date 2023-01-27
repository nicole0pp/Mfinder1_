export interface IListDetails {
  id: number;
}

export type NewListDetails = Omit<IListDetails, 'id'> & { id: null };
