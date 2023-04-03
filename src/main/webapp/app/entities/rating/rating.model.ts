export interface IRating {
  id: number;
  comment?: string | null;
  rating?: number | null;
}

export type NewRating = Omit<IRating, 'id'> & { id: null };
