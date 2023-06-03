import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRatingEvent } from '../rating-event.model';
import { RatingEventService } from '../service/rating-event.service';

@Injectable({ providedIn: 'root' })
export class RatingEventRoutingResolveService implements Resolve<IRatingEvent | null> {
  constructor(protected service: RatingEventService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRatingEvent | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rating: HttpResponse<IRatingEvent>) => {
          if (rating.body) {
            return of(rating.body);
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
