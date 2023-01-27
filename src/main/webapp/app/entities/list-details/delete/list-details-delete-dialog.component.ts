import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IListDetails } from '../list-details.model';
import { ListDetailsService } from '../service/list-details.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './list-details-delete-dialog.component.html',
})
export class ListDetailsDeleteDialogComponent {
  listDetails?: IListDetails;

  constructor(protected listDetailsService: ListDetailsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.listDetailsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
