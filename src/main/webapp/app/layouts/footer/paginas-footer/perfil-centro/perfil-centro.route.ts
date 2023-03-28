import { Route } from '@angular/router';

import { PerfilCentroComponent } from './perfil-centro.component';

export const PERFILCENTRO_ROUTE: Route = {
  path: '',
  component: PerfilCentroComponent,
  data: {
    pageTitle: 'perfil-centro.title',
  },
};
