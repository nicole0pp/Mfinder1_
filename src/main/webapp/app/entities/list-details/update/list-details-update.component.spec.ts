import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ListDetailsFormService } from './list-details-form.service';
import { ListDetailsService } from '../service/list-details.service';
import { IListDetails } from '../list-details.model';

import { ListDetailsUpdateComponent } from './list-details-update.component';

describe('ListDetails Management Update Component', () => {
  let comp: ListDetailsUpdateComponent;
  let fixture: ComponentFixture<ListDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let listDetailsFormService: ListDetailsFormService;
  let listDetailsService: ListDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ListDetailsUpdateComponent],
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
      .overrideTemplate(ListDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ListDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    listDetailsFormService = TestBed.inject(ListDetailsFormService);
    listDetailsService = TestBed.inject(ListDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const listDetails: IListDetails = { id: 456 };

      activatedRoute.data = of({ listDetails });
      comp.ngOnInit();

      expect(comp.listDetails).toEqual(listDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListDetails>>();
      const listDetails = { id: 123 };
      jest.spyOn(listDetailsFormService, 'getListDetails').mockReturnValue(listDetails);
      jest.spyOn(listDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: listDetails }));
      saveSubject.complete();

      // THEN
      expect(listDetailsFormService.getListDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(listDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(listDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListDetails>>();
      const listDetails = { id: 123 };
      jest.spyOn(listDetailsFormService, 'getListDetails').mockReturnValue({ id: null });
      jest.spyOn(listDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: listDetails }));
      saveSubject.complete();

      // THEN
      expect(listDetailsFormService.getListDetails).toHaveBeenCalled();
      expect(listDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IListDetails>>();
      const listDetails = { id: 123 };
      jest.spyOn(listDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ listDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(listDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
