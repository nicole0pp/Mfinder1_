import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAlbum, NewAlbum } from '../album.model';
import { ISong } from 'app/entities/song/song.model';

export type PartialUpdateAlbum = Partial<IAlbum> & Pick<IAlbum, 'id'>;

type RestOf<T extends IAlbum | NewAlbum> = Omit<T, 'publicationDate'> & {
  publicationDate?: string | null;
};

export type RestAlbum = RestOf<IAlbum>;

export type NewRestAlbum = RestOf<NewAlbum>;

// export type PartialUpdateRestAlbum = RestOf<PartialUpdateAlbum>;

export type EntityResponseType = HttpResponse<IAlbum>;
export type EntityArrayResponseType = HttpResponse<IAlbum[]>;

@Injectable({ providedIn: 'root' })
export class AlbumService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/albums');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(album: NewAlbum): Observable<IAlbum> {
    // const copy = this.convertDateFromClient(album);
    return this.http.post<IAlbum>(this.resourceUrl, album);
    // .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(album: IAlbum): Observable<IAlbum> {
    // const copy = this.convertDateFromClient(album);
    return this.http.put<IAlbum>(`${this.resourceUrl}/${this.getAlbumIdentifier(album)}`, album);
    // .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(album: PartialUpdateAlbum): Observable<IAlbum> {
    return this.http.patch<IAlbum>(`${this.resourceUrl}/${this.getAlbumIdentifier(album)}`, album);
    // .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<IAlbum> {
    return this.http.get<IAlbum>(`${this.resourceUrl}/${id}`);
    // .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<HttpResponse<IAlbum[]>> {
    const options = createRequestOption(req);
    return this.http.get<IAlbum[]>(this.resourceUrl, { params: options, observe: 'response' });
    // .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAlbumIdentifier(album: Pick<IAlbum, 'id'>): number {
    return album.id;
  }

  compareAlbum(o1: Pick<IAlbum, 'id'> | null, o2: Pick<IAlbum, 'id'> | null): boolean {
    return o1 && o2 ? this.getAlbumIdentifier(o1) === this.getAlbumIdentifier(o2) : o1 === o2;
  }

  addAlbumToCollectionIfMissing<Type extends Pick<IAlbum, 'id'>>(
    albumCollection: Type[],
    ...albumsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const albums: Type[] = albumsToCheck.filter(isPresent);
    if (albums.length > 0) {
      const albumCollectionIdentifiers = albumCollection.map(albumItem => this.getAlbumIdentifier(albumItem)!);
      const albumsToAdd = albums.filter(albumItem => {
        const albumIdentifier = this.getAlbumIdentifier(albumItem);
        if (albumCollectionIdentifiers.includes(albumIdentifier)) {
          return false;
        }
        albumCollectionIdentifiers.push(albumIdentifier);
        return true;
      });
      return [...albumsToAdd, ...albumCollection];
    }
    return albumCollection;
  }

  // protected convertDateFromClient<T extends IAlbum | NewAlbum | PartialUpdateAlbum>(album: T): RestOf<T> {
  //   return {
  //     ...album,
  //     publicationDate: album.publicationDate?.format(DATE_FORMAT) ?? null,
  //   };
  // }

  // protected convertDateFromServer(restAlbum: RestAlbum): IAlbum {
  //   return {
  //     ...restAlbum,
  //     publicationDate: restAlbum.publicationDate ? dayjs(restAlbum.publicationDate) : undefined,
  //   };
  // }

  // protected convertResponseFromServer(res: HttpResponse<RestAlbum>): HttpResponse<IAlbum> {
  //   return res.clone({
  //     body: res.body ? this.convertDateFromServer(res.body) : null,
  //   });
  // }

  // protected convertResponseArrayFromServer(res: HttpResponse<RestAlbum[]>): HttpResponse<IAlbum[]> {
  //   return res.clone({
  //     body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
  //   });
  // }
}
