import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SongFormService, SongFormGroup } from './song-form.service';
import { ISong } from '../song.model';
import { SongService } from '../service/song.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IListDetails } from 'app/entities/list-details/list-details.model';
import { ListDetailsService } from 'app/entities/list-details/service/list-details.service';
import { IAlbum } from 'app/entities/album/album.model';
import { AlbumService } from 'app/entities/album/service/album.service';
import { MusicGenre } from 'app/entities/enumerations/music-genre.model';
import { Duration } from 'dayjs/esm/plugin/duration';
import { ITEM_SAVED_ALBUM, ITEM_SAVED_SONG } from 'app/config/navigation.constants';

@Component({
  selector: 'jhi-song-updateSong',
  templateUrl: './song-update.component.html',
})
export class SongEditComponent implements OnInit {
  album?: IAlbum;
  isSaving = false;
  song: ISong | null = null;
  albumId: string | null = null;
  musicGenreValues = Object.keys(MusicGenre);

  editForm: SongFormGroup = this.songFormService.createSongFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected songService: SongService,
    protected songFormService: SongFormService,
    protected listDetailsService: ListDetailsService,
    protected albumService: AlbumService,
    protected router: Router,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareListDetails = (o1: IListDetails | null, o2: IListDetails | null): boolean => this.listDetailsService.compareListDetails(o1, o2);

  compareAlbum = (o1: IAlbum | null, o2: IAlbum | null): boolean => this.albumService.compareAlbum(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ song }) => {
      this.song = song;
      if (song) {
        this.updateForm(song);
      }
    });
    this.albumId = this.activatedRoute.snapshot.paramMap.get('id');
    console.log(this.albumId);
  }

  previousState(): void {
    window.history.back();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('mFinder1App.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  getAudioDuration(event: Event) {
    const audioElement = event.target as HTMLAudioElement;
    if (this.editForm.get('audio')) {
      this.editForm.get('duration')?.setValue(audioElement.duration);
      console.log(this.editForm.get('duration')?.value);
    }
  }

  save(): void {
    this.isSaving = true;
    const song = this.songFormService.getSong(this.editForm);
    if (song.id !== null) {
      this.subscribeToSaveResponse(this.songService.update(song));
    } else {
      song.album = this.album;
      song.artist = this.album?.artist;
      console.log(song);
      this.subscribeToSaveResponse(this.songService.create(song));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISong>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(song: ISong): void {
    this.song = song;
    this.songFormService.resetForm(this.editForm, song);
  }
}
