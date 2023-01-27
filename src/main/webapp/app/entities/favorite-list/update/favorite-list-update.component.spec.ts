import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FavoriteListFormService } from './favorite-list-form.service';
import { FavoriteListService } from '../service/favorite-list.service';
import { IFavoriteList } from '../favorite-list.model';
import { IListDetails } from 'app/entities/list-details/list-details.model';
import { ListDetailsService } from 'app/entities/list-details/service/list-details.service';
import { IArtist } from 'app/entities/artist/artist.model';
import { ArtistService } from 'app/entities/artist/service/artist.service';
import { IClient } from 'app/entities/client/client.model';
import { ClientService } from 'app/entities/client/service/client.service';

import { FavoriteListUpdateComponent } from './favorite-list-update.component';

describe('FavoriteList Management Update Component', () => {
  let comp: FavoriteListUpdateComponent;
  let fixture: ComponentFixture<FavoriteListUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let favoriteListFormService: FavoriteListFormService;
  let favoriteListService: FavoriteListService;
  let listDetailsService: ListDetailsService;
  let artistService: ArtistService;
  let clientService: ClientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FavoriteListUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(FavoriteListUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FavoriteListUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    favoriteListFormService = TestBed.inject(FavoriteListFormService);
    favoriteListService = TestBed.inject(FavoriteListService);
    listDetailsService = TestBed.inject(ListDetailsService);
    artistService = TestBed.inject(ArtistService);
    clientService = TestBed.inject(ClientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ListDetails query and add missing value', () => {
      const favoriteList: IFavoriteList = { id: 456 };
      const listDetails: IListDetails = { id: 35769 };
      favoriteList.listDetails = listDetails;

      const listDetailsCollection: IListDetails[] = [{ id: 20192 }];
      jest.spyOn(listDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: listDetailsCollection })));
      const additionalListDetails = [listDetails];
      const expectedCollection: IListDetails[] = [...additionalListDetails, ...listDetailsCollection];
      jest.spyOn(listDetailsService, 'addListDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      expect(listDetailsService.query).toHaveBeenCalled();
      expect(listDetailsService.addListDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        listDetailsCollection,
        ...additionalListDetails.map(expect.objectContaining)
      );
      expect(comp.listDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Artist query and add missing value', () => {
      const favoriteList: IFavoriteList = { id: 456 };
      const artist: IArtist = { id: 27456 };
      favoriteList.artist = artist;

      const artistCollection: IArtist[] = [{ id: 44429 }];
      jest.spyOn(artistService, 'query').mockReturnValue(of(new HttpResponse({ body: artistCollection })));
      const additionalArtists = [artist];
      const expectedCollection: IArtist[] = [...additionalArtists, ...artistCollection];
      jest.spyOn(artistService, 'addArtistToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      expect(artistService.query).toHaveBeenCalled();
      expect(artistService.addArtistToCollectionIfMissing).toHaveBeenCalledWith(
        artistCollection,
        ...additionalArtists.map(expect.objectContaining)
      );
      expect(comp.artistsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Client query and add missing value', () => {
      const favoriteList: IFavoriteList = { id: 456 };
      const client: IClient = { id: 19118 };
      favoriteList.client = client;

      const clientCollection: IClient[] = [{ id: 59570 }];
      jest.spyOn(clientService, 'query').mockReturnValue(of(new HttpResponse({ body: clientCollection })));
      const additionalClients = [client];
      const expectedCollection: IClient[] = [...additionalClients, ...clientCollection];
      jest.spyOn(clientService, 'addClientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      expect(clientService.query).toHaveBeenCalled();
      expect(clientService.addClientToCollectionIfMissing).toHaveBeenCalledWith(
        clientCollection,
        ...additionalClients.map(expect.objectContaining)
      );
      expect(comp.clientsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const favoriteList: IFavoriteList = { id: 456 };
      const listDetails: IListDetails = { id: 19878 };
      favoriteList.listDetails = listDetails;
      const artist: IArtist = { id: 48508 };
      favoriteList.artist = artist;
      const client: IClient = { id: 20605 };
      favoriteList.client = client;

      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      expect(comp.listDetailsSharedCollection).toContain(listDetails);
      expect(comp.artistsSharedCollection).toContain(artist);
      expect(comp.clientsSharedCollection).toContain(client);
      expect(comp.favoriteList).toEqual(favoriteList);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoriteList>>();
      const favoriteList = { id: 123 };
      jest.spyOn(favoriteListFormService, 'getFavoriteList').mockReturnValue(favoriteList);
      jest.spyOn(favoriteListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: favoriteList }));
      saveSubject.complete();

      // THEN
      expect(favoriteListFormService.getFavoriteList).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(favoriteListService.update).toHaveBeenCalledWith(expect.objectContaining(favoriteList));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoriteList>>();
      const favoriteList = { id: 123 };
      jest.spyOn(favoriteListFormService, 'getFavoriteList').mockReturnValue({ id: null });
      jest.spyOn(favoriteListService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoriteList: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: favoriteList }));
      saveSubject.complete();

      // THEN
      expect(favoriteListFormService.getFavoriteList).toHaveBeenCalled();
      expect(favoriteListService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFavoriteList>>();
      const favoriteList = { id: 123 };
      jest.spyOn(favoriteListService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ favoriteList });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(favoriteListService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareListDetails', () => {
      it('Should forward to listDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(listDetailsService, 'compareListDetails');
        comp.compareListDetails(entity, entity2);
        expect(listDetailsService.compareListDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareArtist', () => {
      it('Should forward to artistService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(artistService, 'compareArtist');
        comp.compareArtist(entity, entity2);
        expect(artistService.compareArtist).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareClient', () => {
      it('Should forward to clientService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clientService, 'compareClient');
        comp.compareClient(entity, entity2);
        expect(clientService.compareClient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
