import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EventComponent } from './list/event.component';
import { EventDetailComponent } from './detail/event-detail.component';
import { EventUpdateComponent } from './update/event-update.component';
import { EventDeleteDialogComponent } from './delete/event-delete-dialog.component';
import { EventRoutingModule } from './route/event-routing.module';
import { EventUpdateViewComponent } from './update/event-update.view.component';

@NgModule({
  imports: [SharedModule, EventRoutingModule],
  declarations: [EventComponent, EventDetailComponent, EventUpdateComponent, EventUpdateViewComponent, EventDeleteDialogComponent],
})
export class EventModule {}
