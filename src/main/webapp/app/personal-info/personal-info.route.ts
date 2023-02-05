import { Route } from '@angular/router';

import { PersonalComponent } from './personal-info.component';

export const PERSONAL_MODULE: Route = {
  path: '',
  component: PersonalComponent,
  data: {
    pageTitle: 'personal-info.title',
  },
};
