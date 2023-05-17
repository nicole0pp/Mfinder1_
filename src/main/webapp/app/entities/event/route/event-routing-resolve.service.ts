// import { Injectable } from '@angular/core';
// import { HttpResponse } from '@angular/common/http';
// import { Resolve, ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
// import { Observable, of, EMPTY, OperatorFunction } from 'rxjs';
// import { map, mergeMap } from 'rxjs/operators';

// import { IEvent } from '../event.model';
// import { EventService } from '../service/event.service';

// @Injectable({ providedIn: 'root' })
// export class EventRoutingResolveService implements Resolve<IEvent | null> {
//   constructor(protected service: EventService, protected router: Router) {}
//   resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): IEvent | Observable<IEvent | null> | Promise<IEvent | null> | null {
//     throw new Error('Method not implemented.');
//   }

//   // resolve(route: ActivatedRouteSnapshot): Observable<IEvent | null | never> {
//   //   const id = route.params['id'];
//   //   if (id) {
//   //     return this.service.find(id).pipe(
//   //       mergeMap((event: HttpResponse<IEvent>) => {
//   //         if (event) {
//   //           return event;
//   //         } else {
//   //           this.router.navigate(['404']);
//   //           return EMPTY;
//   //         }
//   //       })
//   //     );
//   //   }
//   //   return of(null);
//   // }
// }
