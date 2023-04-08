import { Route } from '@angular/router';

import { PlataformaComponent } from './plataforma.component';

export const PLATAFORMA_ROUTE: Route = {
  path: '',
  component: PlataformaComponent,
  data: {
    pageTitle: 'plataforma.title',
  },
};
