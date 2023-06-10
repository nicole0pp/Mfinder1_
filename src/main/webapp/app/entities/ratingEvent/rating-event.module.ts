import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RatingEventComponent } from './list/rating-event.component';
import { RatingEventDeleteDialogComponent } from './delete/rating-event-delete-dialog.component';
import { RatingEventRoutingModule } from './route/rating-event-routing.module';

@NgModule({
  imports: [SharedModule, RatingEventRoutingModule],
  declarations: [RatingEventComponent, RatingEventDeleteDialogComponent],
})
export class RatingEventModule {}
