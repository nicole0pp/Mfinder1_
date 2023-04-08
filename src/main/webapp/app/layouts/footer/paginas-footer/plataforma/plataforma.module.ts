import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PLATAFORMA_ROUTE } from './plataforma.route';
import { PlataformaComponent } from './plataforma.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from '../../..//../config/font-awesome-icons';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([PLATAFORMA_ROUTE])],
  declarations: [PlataformaComponent],
})
export class PlataformaModule {
  constructor(iconLibrary: FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
