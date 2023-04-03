export interface IArtist {
  id: number;
  insta_link?: string | null;
  spoti_link?: string | null;
}

export type NewArtist = Omit<IArtist, 'id'> & { id: null };
