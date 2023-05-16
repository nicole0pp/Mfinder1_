import { TipoEvento } from 'app/entities/enumerations/tipo-evento.model';
import dayjs from 'dayjs/esm';
import { City } from '../enumerations/city.model';

export interface IEvent {
  id: number;
  name?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  tipoEvento?: TipoEvento | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  location?: string | null;
  city?: City | null;
  description?: string | null;
  seatCapacity?: number | null;
  artists?: string[] | null;
  ratings?: string[] | null;
}

export type NewEvent = Omit<IEvent, 'id'> & { id: null };
