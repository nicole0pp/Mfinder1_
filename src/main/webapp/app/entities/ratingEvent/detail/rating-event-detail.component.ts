import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRatingEvent } from '../rating-event.model';

@Component({
  selector: 'jhi-rating-detail',
  templateUrl: './rating-event-detail.component.html',
})
export class RatingEventDetailComponent implements OnInit {
  ratingEvent: IRatingEvent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ratingEvent }) => {
      this.ratingEvent = ratingEvent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
