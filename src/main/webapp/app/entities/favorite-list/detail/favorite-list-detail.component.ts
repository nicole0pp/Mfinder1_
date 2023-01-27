import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFavoriteList } from '../favorite-list.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-favorite-list-detail',
  templateUrl: './favorite-list-detail.component.html',
})
export class FavoriteListDetailComponent implements OnInit {
  favoriteList: IFavoriteList | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoriteList }) => {
      this.favoriteList = favoriteList;
    });
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
}
