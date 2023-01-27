import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListDetails } from '../list-details.model';

@Component({
  selector: 'jhi-list-details-detail',
  templateUrl: './list-details-detail.component.html',
})
export class ListDetailsDetailComponent implements OnInit {
  listDetails: IListDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listDetails }) => {
      this.listDetails = listDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
