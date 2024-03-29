import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SongComponent } from './list/song.component';
import { SongDetailComponent } from './detail/song-detail.component';
import { SongUpdateComponent } from './update/song-update.component';
import { SongDeleteDialogComponent } from './delete/song-delete-dialog.component';
import { SongRoutingModule } from './route/song-routing.module';
import { SongEditComponent } from './update/song-edit.component';
import { SongGenreComponent } from './list/songGenre.component';

@NgModule({
  imports: [SharedModule, SongRoutingModule],
  declarations: [SongComponent, SongGenreComponent, SongDetailComponent, SongUpdateComponent, SongDeleteDialogComponent, SongEditComponent],
})
export class SongModule {}
