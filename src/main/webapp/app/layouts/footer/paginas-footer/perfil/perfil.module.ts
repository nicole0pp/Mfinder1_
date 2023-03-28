import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PERFIL_ROUTE } from './perfil.route';
import { PerfilComponent } from './perfil.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from '../../..//../config/font-awesome-icons';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([PERFIL_ROUTE])],
  declarations: [PerfilComponent],
})
export class PerfilModule {
  constructor(iconLibrary: FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
