import { TipoEvento } from 'app/entities/enumerations/tipo-evento.model';
import dayjs from 'dayjs/esm';

export interface IEvent {
  id: number;
  name?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  tipoEvento?: TipoEvento | null;
  startDate?: Date | null;
  endDate?: Date | null;
  location?: string | null;
  city?: string | null;
  description?: string | null;
  seatCapacity?: number | null;
  artists?: string[] | null;
  ratings?: string[] | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
