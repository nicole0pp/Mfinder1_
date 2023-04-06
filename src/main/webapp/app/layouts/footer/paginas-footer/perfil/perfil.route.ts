import { Route } from '@angular/router';

import { PerfilComponent } from './perfil.component';

export const PERFIL_ROUTE: Route = {
  path: '',
  component: PerfilComponent,
  data: {
    pageTitle: 'perfil.title',
  },
};
