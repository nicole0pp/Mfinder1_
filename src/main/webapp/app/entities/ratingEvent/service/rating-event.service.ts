import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRatingEvent, NewRatingEvent } from '../rating-event.model';

export type PartialUpdateRating = Partial<IRatingEvent> & Pick<IRatingEvent, 'id'>;

// type RestOf<T extends IRatingEvent | NewRatingEvent> = Omit<T, 'eventDate'>

export type PartialUpdateRatings = Partial<IRatingEvent> & Pick<IRatingEvent, 'id'>;

export type EntityResponseType = HttpResponse<IRatingEvent>;
export type EntityArrayResponseType = HttpResponse<IRatingEvent[]>;

@Injectable({ providedIn: 'root' })
export class RatingEventService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ratingsEvent');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ratingEvent: NewRatingEvent): Observable<EntityResponseType> {
    return this.http.post<IRatingEvent>(this.resourceUrl, ratingEvent, { observe: 'response' });
  }

  update(ratingEvent: IRatingEvent): Observable<EntityResponseType> {
    return this.http.put<IRatingEvent>(`${this.resourceUrl}/${this.getRatingEventIdentifier(ratingEvent)}`, ratingEvent, {
      observe: 'response',
    });
  }

  partialUpdate(ratingEvent: PartialUpdateRating): Observable<EntityResponseType> {
    return this.http.patch<IRatingEvent>(`${this.resourceUrl}/${this.getRatingEventIdentifier(ratingEvent)}`, ratingEvent, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRatingEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRatingEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getRatingsByEvent(eventId: number): Observable<HttpResponse<IRatingEvent[]>> {
    return this.http.get<IRatingEvent[]>(`${this.resourceUrl}/eventId/${eventId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRatingEventIdentifier(ratingEvent: Pick<IRatingEvent, 'id'>): number {
    return ratingEvent.id;
  }

  compareRating(o1: Pick<IRatingEvent, 'id'> | null, o2: Pick<IRatingEvent, 'id'> | null): boolean {
    return o1 && o2 ? this.getRatingEventIdentifier(o1) === this.getRatingEventIdentifier(o2) : o1 === o2;
  }

  addRatingEventToCollectionIfMissing<Type extends Pick<IRatingEvent, 'id'>>(
    ratingEventCollection: Type[],
    ...ratingEventsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ratingEvent: Type[] = ratingEventsToCheck.filter(isPresent);
    if (ratingEvent.length > 0) {
      const ratingEventCollectionIdentifiers = ratingEventCollection.map(
        ratingEventItem => this.getRatingEventIdentifier(ratingEventItem)!
      );
      const ratingEventsToAdd = ratingEvent.filter(ratingEventItem => {
        const ratingEventIdentifier = this.getRatingEventIdentifier(ratingEventItem);
        if (ratingEventCollectionIdentifiers.includes(ratingEventIdentifier)) {
          return false;
        }
        ratingEventCollectionIdentifiers.push(ratingEventIdentifier);
        return true;
      });
      return [...ratingEventsToAdd, ...ratingEventCollection];
    }
    return ratingEventCollection;
  }
}
