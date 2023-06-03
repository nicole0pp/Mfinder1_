import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event',
        data: { pageTitle: 'mFinder1App.event.title' },
        loadChildren: () => import('./event/event.module').then(m => m.EventModule),
      },
      {
        path: 'song',
        data: { pageTitle: 'mFinder1App.song.home.title' },
        loadChildren: () => import('./song/song.module').then(m => m.SongModule),
      },
      {
        path: 'artist',
        data: { pageTitle: 'mFinder1App.artist.home.title' },
        loadChildren: () => import('./artist/artist.module').then(m => m.ArtistModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'mFinder1App.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'album',
        data: { pageTitle: 'mFinder1App.album.home.title' },
        loadChildren: () => import('./album/album.module').then(m => m.AlbumModule),
      },
      {
        path: 'favorite-list',
        data: { pageTitle: 'mFinder1App.favoriteList.home.title' },
        loadChildren: () => import('./favorite-list/favorite-list.module').then(m => m.FavoriteListModule),
      },
      {
        path: 'list-details',
        data: { pageTitle: 'mFinder1App.listDetails.home.title' },
        loadChildren: () => import('./list-details/list-details.module').then(m => m.ListDetailsModule),
      },
      {
        path: 'rating',
        data: { pageTitle: 'mFinder1App.rating.home.title' },
        loadChildren: () => import('./rating/rating-event.module').then(m => m.RatingEventModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
