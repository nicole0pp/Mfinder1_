import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { ARTISTVIEW_MODULE } from './artist-info.route';
import { ArtistViewComponent } from './artist-info.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([ARTISTVIEW_MODULE])],
  declarations: [ArtistViewComponent],
})
export class ArtistViewModule {}
