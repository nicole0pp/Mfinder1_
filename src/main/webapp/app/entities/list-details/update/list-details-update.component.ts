import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ListDetailsFormService, ListDetailsFormGroup } from './list-details-form.service';
import { IListDetails } from '../list-details.model';
import { ListDetailsService } from '../service/list-details.service';

@Component({
  selector: 'jhi-list-details-update',
  templateUrl: './list-details-update.component.html',
})
export class ListDetailsUpdateComponent implements OnInit {
  isSaving = false;
  listDetails: IListDetails | null = null;

  editForm: ListDetailsFormGroup = this.listDetailsFormService.createListDetailsFormGroup();

  constructor(
    protected listDetailsService: ListDetailsService,
    protected listDetailsFormService: ListDetailsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listDetails }) => {
      this.listDetails = listDetails;
      if (listDetails) {
        this.updateForm(listDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const listDetails = this.listDetailsFormService.getListDetails(this.editForm);
    if (listDetails.id !== null) {
      this.subscribeToSaveResponse(this.listDetailsService.update(listDetails));
    } else {
      this.subscribeToSaveResponse(this.listDetailsService.create(listDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListDetails>>): void {
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

  protected updateForm(listDetails: IListDetails): void {
    this.listDetails = listDetails;
    this.listDetailsFormService.resetForm(this.editForm, listDetails);
  }
}
