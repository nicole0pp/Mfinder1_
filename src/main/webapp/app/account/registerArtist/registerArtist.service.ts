import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { RegistrationArtist } from '../registerArtist/registerArtist.model';

@Injectable({ providedIn: 'root' })
export class RegisterArtistService {
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(registrationA: RegistrationArtist): Observable<{}> {
    return this.http.post(this.applicationConfigService.getEndpointFor('api/registerArtist'), registrationA);
  }
}
