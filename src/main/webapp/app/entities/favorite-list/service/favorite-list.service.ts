import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFavoriteList, NewFavoriteList } from '../favorite-list.model';

export type PartialUpdateFavoriteList = Partial<IFavoriteList> & Pick<IFavoriteList, 'id'>;

export type EntityResponseType = HttpResponse<IFavoriteList>;
export type EntityArrayResponseType = HttpResponse<IFavoriteList[]>;

@Injectable({ providedIn: 'root' })
export class FavoriteListService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/favorite-lists');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(favoriteList: NewFavoriteList): Observable<EntityResponseType> {
    return this.http.post<IFavoriteList>(this.resourceUrl, favoriteList, { observe: 'response' });
  }

  update(favoriteList: IFavoriteList): Observable<EntityResponseType> {
    return this.http.put<IFavoriteList>(`${this.resourceUrl}/${this.getFavoriteListIdentifier(favoriteList)}`, favoriteList, {
      observe: 'response',
    });
  }

  partialUpdate(favoriteList: PartialUpdateFavoriteList): Observable<EntityResponseType> {
    return this.http.patch<IFavoriteList>(`${this.resourceUrl}/${this.getFavoriteListIdentifier(favoriteList)}`, favoriteList, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFavoriteList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFavoriteList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFavoriteListIdentifier(favoriteList: Pick<IFavoriteList, 'id'>): number {
    return favoriteList.id;
  }

  compareFavoriteList(o1: Pick<IFavoriteList, 'id'> | null, o2: Pick<IFavoriteList, 'id'> | null): boolean {
    return o1 && o2 ? this.getFavoriteListIdentifier(o1) === this.getFavoriteListIdentifier(o2) : o1 === o2;
  }

  addFavoriteListToCollectionIfMissing<Type extends Pick<IFavoriteList, 'id'>>(
    favoriteListCollection: Type[],
    ...favoriteListsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const favoriteLists: Type[] = favoriteListsToCheck.filter(isPresent);
    if (favoriteLists.length > 0) {
      const favoriteListCollectionIdentifiers = favoriteListCollection.map(
        favoriteListItem => this.getFavoriteListIdentifier(favoriteListItem)!
      );
      const favoriteListsToAdd = favoriteLists.filter(favoriteListItem => {
        const favoriteListIdentifier = this.getFavoriteListIdentifier(favoriteListItem);
        if (favoriteListCollectionIdentifiers.includes(favoriteListIdentifier)) {
          return false;
        }
        favoriteListCollectionIdentifiers.push(favoriteListIdentifier);
        return true;
      });
      return [...favoriteListsToAdd, ...favoriteListCollection];
    }
    return favoriteListCollection;
  }
}
