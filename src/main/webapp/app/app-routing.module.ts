import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'choose.component/account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: '',
          loadChildren: () => import(`./entities/entity-routing.module`).then(m => m.EntityRoutingModule),
        },
        {
          path: 'personal-info.component',
          loadChildren: () => import(`./personal-info/personal-info.module`).then(m => m.PersonsalModule),
        },
        {
          path: 'artist-info.component',
          loadChildren: () => import(`./artist-info/artist-info.module`).then(m => m.ArtistViewModule),
        },
        {
          path: 'choose.component',
          loadChildren: () => import('./chooseAccount/choose.module').then(m => m.ChooseModule),
        },
        {
          path: 'perfil-centro.component',
          loadChildren: () =>
            import('../app/layouts/footer/paginas-footer/perfil-centro/perfil-centro.module').then(m => m.PerfilCentroModule),
        },
        {
          path: 'personal',
          loadChildren: () => import('../app/layouts/footer/paginas-footer/perfil/perfil.module').then(m => m.PerfilModule),
        },
        {
          path: 'plataforma',
          loadChildren: () => import('../app/layouts/footer/paginas-footer/plataforma/plataforma.module').then(m => m.PlataformaModule),
        },

        navbarRoute,
        ...errorRoute,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
