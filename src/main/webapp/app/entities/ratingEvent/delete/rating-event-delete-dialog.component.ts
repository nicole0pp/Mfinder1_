import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRatingEvent } from '../rating-event.model';
import { RatingEventService } from '../service/rating-event.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './rating-event-delete-dialog.component.html',
})
export class RatingEventDeleteDialogComponent {
  ratingEvent?: IRatingEvent;

  constructor(protected ratingEventService: RatingEventService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ratingEventService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
