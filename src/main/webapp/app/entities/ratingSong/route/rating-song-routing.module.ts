import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RatingSongComponent } from '../list/rating-song.component';
import { RatingSongDetailComponent } from '../detail/rating-song-detail.component';
import { RatingSongUpdateComponent } from '../update/rating-song-update.component';
import { RatingSongRoutingResolveService } from './rating-song-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ratingRoute: Routes = [
  {
    path: '',
    component: RatingSongComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RatingSongDetailComponent,
    resolve: {
      rating: RatingSongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RatingSongUpdateComponent,
    resolve: {
      rating: RatingSongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RatingSongUpdateComponent,
    resolve: {
      rating: RatingSongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ratingRoute)],
  exports: [RouterModule],
})
export class RatingSongRoutingModule {}
