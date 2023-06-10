import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RatingEventComponent } from '../list/rating-event.component';
import { RatingEventRoutingResolveService } from './rating-event-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ratingRoute: Routes = [
  {
    path: ':eventId',
    component: RatingEventComponent,
    data: {
      defaultSort: 'eventId,' + ASC,
    },
    resolve: {
      ratingEvent: RatingEventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ratingRoute)],
  exports: [RouterModule],
})
export class RatingEventRoutingModule {}
