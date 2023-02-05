import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { PERSONAL_MODULE } from './personal-info.route';
import { PersonalComponent } from './personal-info.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([PERSONAL_MODULE])],
  declarations: [PersonalComponent],
})
export class PersonsalModule {}
