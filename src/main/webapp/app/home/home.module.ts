import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { PersonalComponent } from 'app/personal-info/personal-info.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ArtistViewComponent } from 'app/artist-info/artist-info.component';

const homeRoute: Routes = [
  {
    path: 'personal-info.component',
    component: PersonalComponent,
  },
  {
    path: 'artist-info.component',
    component: ArtistViewComponent,
  },
];
@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), RouterModule.forChild(homeRoute)],
  declarations: [HomeComponent],
  exports: [RouterModule],
})
export class HomeModule {}
