import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import { Pagination } from 'app/core/request/request.model';
import { IUser } from './user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private resourceUrl = this.applicationConfigService.getEndpointFor('api/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  getUserIdentifier(user: Pick<IUser, 'id'>): number | null {
    return user.id;
  }

  compareUser(o1: Pick<IUser, 'id'> | null, o2: Pick<IUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserIdentifier(o1) === this.getUserIdentifier(o2) : o1 === o2;
  }

  addUserToCollectionIfMissing<Type extends Partial<IUser> & Pick<IUser, 'id'>>(
    userCollection: Type[],
    ...usersToCheck: (Type | null | undefined)[]
  ): IUser[] {
    const users: Type[] = usersToCheck.filter(isPresent);
    if (users.length > 0) {
      const userCollectionIdentifiers = userCollection.map(userItem => this.getUserIdentifier(userItem)!);
      const usersToAdd = users.filter(userItem => {
        const userIdentifier = this.getUserIdentifier(userItem);
        if (userIdentifier) {
          if (userCollectionIdentifiers.includes(userIdentifier)) {
            return false;
          }
          userCollectionIdentifiers.push(userIdentifier);
          return true;
        } else {
          return false;
        }
      });
      return [...usersToAdd, ...userCollection];
    }
    return userCollection;
  }
}
