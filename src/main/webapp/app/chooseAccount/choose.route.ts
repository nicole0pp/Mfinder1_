import { Route, Routes } from '@angular/router';
import { AccountModule } from 'app/account/account.module';

import { ChooseAccountComponent } from './choose.component';

export const ChooseRoute: Route = {
  path: '',
  component: ChooseAccountComponent,
};
