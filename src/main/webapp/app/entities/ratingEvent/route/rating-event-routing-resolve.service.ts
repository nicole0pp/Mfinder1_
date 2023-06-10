import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { IRatingEvent } from '../rating-event.model';
import { RatingEventService } from '../service/rating-event.service';

@Injectable({ providedIn: 'root' })
export class RatingEventRoutingResolveService implements Resolve<IRatingEvent | null> {
  constructor(protected service: RatingEventService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<any> {
    const eventId = parseInt(route.paramMap.get('eventId') ?? '', 10);

    return this.service.getRatingsByEvent(eventId).pipe(
      map((response: HttpResponse<IRatingEvent[]>) => {
        const ratings = response.body;

        if (ratings && ratings.length > 0) {
          return ratings[0];
        } else {
          return catchError(() => of([]));
        }
      }),
      catchError(() => of([]))
    );
  }
}
