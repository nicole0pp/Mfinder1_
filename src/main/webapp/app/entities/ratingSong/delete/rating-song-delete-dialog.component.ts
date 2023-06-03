import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRatingSong } from '../rating-song.model';
import { RatingSongService } from '../service/rating-song.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './rating-event-delete-dialog.component.html',
})
export class RatingSongDeleteDialogComponent {
  ratingSong?: IRatingSong;

  constructor(protected ratingSongService: RatingSongService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ratingSongService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
