import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IListDetails, NewListDetails } from '../list-details.model';

export type PartialUpdateListDetails = Partial<IListDetails> & Pick<IListDetails, 'id'>;

export type EntityResponseType = HttpResponse<IListDetails>;
export type EntityArrayResponseType = HttpResponse<IListDetails[]>;

@Injectable({ providedIn: 'root' })
export class ListDetailsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/list-details');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(listDetails: NewListDetails): Observable<EntityResponseType> {
    return this.http.post<IListDetails>(this.resourceUrl, listDetails, { observe: 'response' });
  }

  update(listDetails: IListDetails): Observable<EntityResponseType> {
    return this.http.put<IListDetails>(`${this.resourceUrl}/${this.getListDetailsIdentifier(listDetails)}`, listDetails, {
      observe: 'response',
    });
  }

  partialUpdate(listDetails: PartialUpdateListDetails): Observable<EntityResponseType> {
    return this.http.patch<IListDetails>(`${this.resourceUrl}/${this.getListDetailsIdentifier(listDetails)}`, listDetails, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IListDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListDetails[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getListDetailsIdentifier(listDetails: Pick<IListDetails, 'id'>): number {
    return listDetails.id;
  }

  compareListDetails(o1: Pick<IListDetails, 'id'> | null, o2: Pick<IListDetails, 'id'> | null): boolean {
    return o1 && o2 ? this.getListDetailsIdentifier(o1) === this.getListDetailsIdentifier(o2) : o1 === o2;
  }

  addListDetailsToCollectionIfMissing<Type extends Pick<IListDetails, 'id'>>(
    listDetailsCollection: Type[],
    ...listDetailsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const listDetails: Type[] = listDetailsToCheck.filter(isPresent);
    if (listDetails.length > 0) {
      const listDetailsCollectionIdentifiers = listDetailsCollection.map(
        listDetailsItem => this.getListDetailsIdentifier(listDetailsItem)!
      );
      const listDetailsToAdd = listDetails.filter(listDetailsItem => {
        const listDetailsIdentifier = this.getListDetailsIdentifier(listDetailsItem);
        if (listDetailsCollectionIdentifiers.includes(listDetailsIdentifier)) {
          return false;
        }
        listDetailsCollectionIdentifiers.push(listDetailsIdentifier);
        return true;
      });
      return [...listDetailsToAdd, ...listDetailsCollection];
    }
    return listDetailsCollection;
  }
}
