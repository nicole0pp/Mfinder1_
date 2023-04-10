import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { RegistrationArtist } from '../registerArtist/registerArtist.model';
import { IArtist } from 'app/entities/artist/artist.model';
@Injectable({ providedIn: 'root' })
export class RegisterArtistService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(registrationA: RegistrationArtist, artistName: IArtist['artistName']): Observable<{}> {
    if (artistName) {
      let params = new HttpParams().set('artistName', artistName);
      return this.http.post(this.applicationConfigService.getEndpointFor('api/registerArtist'), registrationA, {
        params: params,
        observe: 'response',
      });
    } else {
      return this.http.post(this.applicationConfigService.getEndpointFor('api/registerArtist'), { registrationA, artistName });
    }
  }
}
