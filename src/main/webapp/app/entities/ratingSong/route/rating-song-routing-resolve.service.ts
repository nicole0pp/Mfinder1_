import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRatingSong } from '../rating-song.model';
import { RatingSongService } from '../service/rating-song.service';

@Injectable({ providedIn: 'root' })
export class RatingSongRoutingResolveService implements Resolve<IRatingSong | null> {
  constructor(protected service: RatingSongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRatingSong | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((rating: HttpResponse<IRatingSong>) => {
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
