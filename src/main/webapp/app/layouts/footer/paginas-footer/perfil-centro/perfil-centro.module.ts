import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PERFILCENTRO_MODULE } from './perfil-centro.route';
import { PerfilCentroComponent } from './perfil-centro.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from '../../..//../config/font-awesome-icons';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([PERFILCENTRO_MODULE])],
  declarations: [PerfilCentroComponent],
})
export class PerfilCentroModule {
  constructor(iconLibrary: FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
