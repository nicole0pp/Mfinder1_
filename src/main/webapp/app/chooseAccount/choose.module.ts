import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { CHOOSEACOUNT_MODULE } from './choose.route';
import { ChooseAccountComponent } from './choose.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from '../config/font-awesome-icons';
@NgModule({
  imports: [SharedModule, RouterModule.forChild([CHOOSEACOUNT_MODULE])],
  declarations: [ChooseAccountComponent],
})
export class ChooseModule {
  constructor(iconLibrary: FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
