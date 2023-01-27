export interface IClient {
  id: number;
}

export type NewClient = Omit<IClient, 'id'> & { id: null };
