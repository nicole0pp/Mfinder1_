import { Injectable, NgModule } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventComponent } from '../list/event.component';
import { EventDetailComponent } from '../detail/event-detail.component';
import { EventUpdateComponent } from '../update/event-update.component';
// import { EventRoutingResolveService } from './event-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { EventUpdateViewComponent } from '../update/event-update.view.component';
import { IEvent } from '../event.model';
import { EventService } from '../service/event.service';
import { Observable, of } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class EventManagementResolve implements Resolve<IEvent | null> {
  constructor(private service: EventService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvent | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}
const eventRoute: Routes = [
  {
    path: '',
    component: EventComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EventDetailComponent,
    resolve: {
      event: EventManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventUpdateComponent,
    resolve: {
      Event: EventManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventUpdateComponent,
    resolve: {
      Event: EventManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/update-view',
    component: EventUpdateViewComponent,
    resolve: {
      Event: EventManagementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventRoute)],
  exports: [RouterModule],
})
export class EventRoutingModule {}
