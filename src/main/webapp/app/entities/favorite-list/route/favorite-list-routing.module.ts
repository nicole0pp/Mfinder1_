import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FavoriteListComponent } from '../list/favorite-list.component';
import { FavoriteListDetailComponent } from '../detail/favorite-list-detail.component';
import { FavoriteListUpdateComponent } from '../update/favorite-list-update.component';
import { FavoriteListRoutingResolveService } from './favorite-list-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const favoriteListRoute: Routes = [
  {
    path: '',
    component: FavoriteListComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FavoriteListDetailComponent,
    resolve: {
      favoriteList: FavoriteListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FavoriteListUpdateComponent,
    resolve: {
      favoriteList: FavoriteListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FavoriteListUpdateComponent,
    resolve: {
      favoriteList: FavoriteListRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(favoriteListRoute)],
  exports: [RouterModule],
})
export class FavoriteListRoutingModule {}
