export interface IArtist {
  id: number;
}

export type NewArtist = Omit<IArtist, 'id'> & { id: null };
