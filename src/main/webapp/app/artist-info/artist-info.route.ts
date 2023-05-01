import { Route, Routes } from '@angular/router';

import { ArtistViewComponent } from './artist-info.component';

import { UserManagementResolve } from '../admin/user-management/user-management.route';
import { UserManagementDetailComponent } from 'app/admin/user-management/detail/user-management-detail.component';

export const ARTISTVIEW_MODULE: Routes = [
  {
    path: '',
    component: ArtistViewComponent,
  },
];
