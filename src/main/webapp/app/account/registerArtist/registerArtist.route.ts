import { Route } from '@angular/router';

import { RegisterArtistComponent } from './registerArtist.component';

export const registerArtistRoute: Route = {
  path: 'registerArtist',
  component: RegisterArtistComponent,
  data: {
    pageTitle: 'register.title',
  },
};
