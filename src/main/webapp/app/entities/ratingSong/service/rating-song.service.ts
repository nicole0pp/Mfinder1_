import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRatingSong, NewRatingSong } from '../rating-song.model';

export type PartialUpdateRating = Partial<IRatingSong> & Pick<IRatingSong, 'id'>;

export type EntityResponseType = HttpResponse<IRatingSong>;
export type EntityArrayResponseType = HttpResponse<IRatingSong[]>;

@Injectable({ providedIn: 'root' })
export class RatingSongService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ratingsSong');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ratingSong: NewRatingSong): Observable<EntityResponseType> {
    return this.http.post<IRatingSong>(this.resourceUrl, ratingSong, { observe: 'response' });
  }

  update(ratingSong: IRatingSong): Observable<EntityResponseType> {
    return this.http.put<IRatingSong>(`${this.resourceUrl}/${this.getRatingSongIdentifier(ratingSong)}`, ratingSong, {
      observe: 'response',
    });
  }

  partialUpdate(ratingSong: PartialUpdateRating): Observable<EntityResponseType> {
    return this.http.patch<IRatingSong>(`${this.resourceUrl}/${this.getRatingSongIdentifier(ratingSong)}`, ratingSong, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRatingSong>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRatingSong[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRatingSongIdentifier(ratingSong: Pick<IRatingSong, 'id'>): number {
    return ratingSong.id;
  }

  compareRating(o1: Pick<IRatingSong, 'id'> | null, o2: Pick<IRatingSong, 'id'> | null): boolean {
    return o1 && o2 ? this.getRatingSongIdentifier(o1) === this.getRatingSongIdentifier(o2) : o1 === o2;
  }

  addRatingSongToCollectionIfMissing<Type extends Pick<IRatingSong, 'id'>>(
    ratingSongCollection: Type[],
    ...ratingSongsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ratingSong: Type[] = ratingSongsToCheck.filter(isPresent);
    if (ratingSong.length > 0) {
      const ratingSongCollectionIdentifiers = ratingSongCollection.map(ratingSongItem => this.getRatingSongIdentifier(ratingSongItem)!);
      const ratingSongsToAdd = ratingSong.filter(ratingSongItem => {
        const ratingSongIdentifier = this.getRatingSongIdentifier(ratingSongItem);
        if (ratingSongCollectionIdentifiers.includes(ratingSongIdentifier)) {
          return false;
        }
        ratingSongCollectionIdentifiers.push(ratingSongIdentifier);
        return true;
      });
      return [...ratingSongsToAdd, ...ratingSongCollection];
    }
    return ratingSongCollection;
  }
}
