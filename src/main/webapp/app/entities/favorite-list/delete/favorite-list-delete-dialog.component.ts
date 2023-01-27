import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFavoriteList } from '../favorite-list.model';
import { FavoriteListService } from '../service/favorite-list.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './favorite-list-delete-dialog.component.html',
})
export class FavoriteListDeleteDialogComponent {
  favoriteList?: IFavoriteList;

  constructor(protected favoriteListService: FavoriteListService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.favoriteListService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
