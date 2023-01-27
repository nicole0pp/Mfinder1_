import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ListDetailsComponent } from './list/list-details.component';
import { ListDetailsDetailComponent } from './detail/list-details-detail.component';
import { ListDetailsUpdateComponent } from './update/list-details-update.component';
import { ListDetailsDeleteDialogComponent } from './delete/list-details-delete-dialog.component';
import { ListDetailsRoutingModule } from './route/list-details-routing.module';

@NgModule({
  imports: [SharedModule, ListDetailsRoutingModule],
  declarations: [ListDetailsComponent, ListDetailsDetailComponent, ListDetailsUpdateComponent, ListDetailsDeleteDialogComponent],
})
export class ListDetailsModule {}
