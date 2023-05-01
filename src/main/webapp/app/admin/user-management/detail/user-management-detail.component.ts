import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from '../user-management.model';
import { IArtist } from 'app/entities/artist/artist.model';
import { ArtistService } from 'app/entities/artist/service/artist.service';

@Component({
  selector: 'jhi-user-mgmt-detail',
  templateUrl: './user-management-detail.component.html',
})
export class UserManagementDetailComponent implements OnInit {
  user: User | null = null;

  constructor(private route: ActivatedRoute, artistService: ArtistService) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user, artist }) => {
      this.user = user;
    });
  }
  //"/artist/user/14/view"
  //"/admin/user-management/admin/view"
  redireccionar() {
    const rutaActual = window.location.pathname;
    if (rutaActual.includes('/artist/user')) {
      window.location.href = '/artist';
    } else if (rutaActual.includes('/admin/user-management')) {
      window.location.href = '/admin/user-management';
    } else {
      // Redirigir a una ruta por defecto si la ruta actual no coincide con ninguna de las anteriores
      window.location.href = '/admin/user-management';
    }
  }
}
