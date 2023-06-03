import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RatingSongComponent } from './list/rating-song.component';
import { RatingSongDetailComponent } from './detail/rating-song-detail.component';
import { RatingSongUpdateComponent } from './update/rating-song-update.component';
import { RatingSongDeleteDialogComponent } from './delete/rating-song-delete-dialog.component';
import { RatingRoutingModule } from './route/rating-routing.module';

@NgModule({
  imports: [SharedModule, RatingRoutingModule],
  declarations: [RatingSongComponent, RatingSongDetailComponent, RatingSongUpdateComponent, RatingSongDeleteDialogComponent],
})
export class RatingSongModule {}
