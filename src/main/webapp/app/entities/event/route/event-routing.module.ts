import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EventComponent } from '../list/event.component';
import { EventDetailComponent } from '../detail/event-detail.component';
import { EventUpdateComponent } from '../update/event-update.component';
import { EventRoutingResolveService } from './event-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { EventUpdateViewComponent } from '../update/event-update.view.component';

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
      event: EventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EventUpdateComponent,
    resolve: {
      event: EventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EventUpdateComponent,

    resolve: {
      event: EventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/update-view',
    component: EventUpdateViewComponent,
    resolve: {
      event: EventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(eventRoute)],
  exports: [RouterModule],
})
export class EventRoutingModule {}
