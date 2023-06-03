import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRatingSong } from '../rating-song.model';

@Component({
  selector: 'jhi-rating-detail',
  templateUrl: './rating-song-detail.component.html',
})
export class RatingSongDetailComponent implements OnInit {
  ratingSong: IRatingSong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratingSong }) => {
      this.ratingSong = ratingSong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
