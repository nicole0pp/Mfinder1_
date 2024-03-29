import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ClientComponent } from '../list/client.component';
import { ClientDetailComponent } from '../detail/client-detail.component';
import { ClientUpdateComponent } from '../update/client-update.component';
import { ClientRoutingResolveService } from './client-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';
import { UserManagementDetailComponent } from 'app/admin/user-management/detail/user-management-detail.component';
import { UserManagementResolveById } from 'app/admin/user-management/user-management.route';

const clientRoute: Routes = [
  {
    path: '',
    component: ClientComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'user/:id/view',
    component: UserManagementDetailComponent,
    resolve: {
      user: UserManagementResolveById,
    },
  },
  {
    path: ':id/view',
    component: ClientDetailComponent,
    resolve: {
      client: ClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClientUpdateComponent,
    resolve: {
      client: ClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClientUpdateComponent,
    resolve: {
      client: ClientRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(clientRoute)],
  exports: [RouterModule],
})
export class ClientRoutingModule {}
