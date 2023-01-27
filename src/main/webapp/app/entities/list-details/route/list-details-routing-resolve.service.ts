import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IListDetails } from '../list-details.model';
import { ListDetailsService } from '../service/list-details.service';

@Injectable({ providedIn: 'root' })
export class ListDetailsRoutingResolveService implements Resolve<IListDetails | null> {
  constructor(protected service: ListDetailsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListDetails | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((listDetails: HttpResponse<IListDetails>) => {
          if (listDetails.body) {
            return of(listDetails.body);
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
