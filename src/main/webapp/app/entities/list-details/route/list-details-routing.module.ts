import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ListDetailsComponent } from '../list/list-details.component';
import { ListDetailsDetailComponent } from '../detail/list-details-detail.component';
import { ListDetailsUpdateComponent } from '../update/list-details-update.component';
import { ListDetailsRoutingResolveService } from './list-details-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const listDetailsRoute: Routes = [
  {
    path: '',
    component: ListDetailsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ListDetailsDetailComponent,
    resolve: {
      listDetails: ListDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ListDetailsUpdateComponent,
    resolve: {
      listDetails: ListDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ListDetailsUpdateComponent,
    resolve: {
      listDetails: ListDetailsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(listDetailsRoute)],
  exports: [RouterModule],
})
export class ListDetailsRoutingModule {}
