import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, map, Observable, switchMap, take, tap } from 'rxjs';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { IAlbum } from '../album.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { IArtist } from 'app/entities/artist/artist.model';
import { ArtistService, EntityArrayResponseType } from 'app/entities/artist/service/artist.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SongUpdateComponent } from 'app/entities/song/update/song-update.component';
import { ITEM_DELETED_SONG, ITEM_SAVED_ALBUM } from 'app/config/navigation.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { AlbumComponent } from '../list/album.component';
import { ISong } from 'app/entities/song/song.model';
import { AlbumService } from '../service/album.service';
import { IEvent } from 'app/entities/event/event.model';
import { SongService } from 'app/entities/song/service/song.service';
import { SongDeleteDialogComponent } from 'app/entities/song/delete/song-delete-dialog.component';

@Component({
  selector: 'jhi-album-detail',
  templateUrl: './album-detail.component.html',
})
export class AlbumDetailComponent implements OnInit {
  album: IAlbum | null = null;
  songs?: ISong[];
  totalItems = 0;

  constructor(
    protected dataUtils: DataUtils,
    protected activatedRoute: ActivatedRoute,
    protected albumService: AlbumService,
    protected songService: SongService,
    protected router: Router,
    protected artistService: ArtistService,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ album }) => {
      this.album = album;
    });
    this.songService.getAllSongsByAlbum(this.album?.id).subscribe(response => {
      this.fillComponentAttributesFromResponseHeader(response.headers);
      this.songs = response.body as IEvent[];
      // this.songs?.sort((a, b) => (dayjs(b.startDate).isBefore(a.startDate) ? -1 : 1));
    });
  }
  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }

  createSong(album: IAlbum): void {
    const modalRef = this.modalService.open(SongUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.album = album;
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_SAVED_ALBUM),
        take(1)
      )
      .subscribe(() => {
        this.router.navigate([album.id, '/view']);
      });
  }

  delete(song: ISong): void {
    const modalRef = this.modalService.open(SongDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.song = song;
    modalRef.closed.pipe(filter(reason => reason === ITEM_DELETED_SONG)).subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onSaveSuccess();
      },
    });
  }
  protected onSaveSuccess(): void {
    this.ngOnInit();
  }
}
