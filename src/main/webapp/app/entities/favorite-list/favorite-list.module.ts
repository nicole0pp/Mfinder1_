import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FavoriteListComponent } from './list/favorite-list.component';
import { FavoriteListDetailComponent } from './detail/favorite-list-detail.component';
import { FavoriteListUpdateComponent } from './update/favorite-list-update.component';
import { FavoriteListDeleteDialogComponent } from './delete/favorite-list-delete-dialog.component';
import { FavoriteListRoutingModule } from './route/favorite-list-routing.module';

@NgModule({
  imports: [SharedModule, FavoriteListRoutingModule],
  declarations: [FavoriteListComponent, FavoriteListDetailComponent, FavoriteListUpdateComponent, FavoriteListDeleteDialogComponent],
})
export class FavoriteListModule {}
