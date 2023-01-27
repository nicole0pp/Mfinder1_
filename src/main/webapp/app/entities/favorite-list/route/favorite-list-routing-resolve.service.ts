import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFavoriteList } from '../favorite-list.model';
import { FavoriteListService } from '../service/favorite-list.service';

@Injectable({ providedIn: 'root' })
export class FavoriteListRoutingResolveService implements Resolve<IFavoriteList | null> {
  constructor(protected service: FavoriteListService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFavoriteList | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((favoriteList: HttpResponse<IFavoriteList>) => {
          if (favoriteList.body) {
            return of(favoriteList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
