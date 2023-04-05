import { NgModule } from '@angular/core';
import { AppRoutingModule } from 'app/app-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { ChooseAccountComponent } from './choose.component';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from '../config/font-awesome-icons';
import { RouterModule } from '@angular/router';
import { ChooseRoute } from './choose.route';
@NgModule({
  imports: [SharedModule, RouterModule.forChild([ChooseRoute]), AppRoutingModule],
  declarations: [ChooseAccountComponent],
  exports: [RouterModule],
})
export class ChooseModule {
  constructor(iconLibrary: FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }
}
