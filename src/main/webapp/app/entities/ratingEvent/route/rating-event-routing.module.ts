import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RatingEventComponent } from '../list/rating-event.component';
import { RatingEventDetailComponent } from '../detail/rating-event-detail.component';
import { RatingEventUpdateComponent } from '../update/rating-event-update.component';
import { RatingEventRoutingResolveService } from './rating-event-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ratingRoute: Routes = [
  {
    path: '',
    component: RatingEventComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RatingEventDetailComponent,
    resolve: {
      rating: RatingEventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RatingEventUpdateComponent,
    resolve: {
      rating: RatingEventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RatingEventUpdateComponent,
    resolve: {
      rating: RatingEventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ratingRoute)],
  exports: [RouterModule],
})
export class RatingEventRoutingModule {}
