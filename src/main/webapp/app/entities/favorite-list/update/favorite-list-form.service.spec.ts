import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../favorite-list.test-samples';

import { FavoriteListFormService } from './favorite-list-form.service';

describe('FavoriteList Form Service', () => {
  let service: FavoriteListFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FavoriteListFormService);
  });

  describe('Service methods', () => {
    describe('createFavoriteListFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFavoriteListFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            picture: expect.any(Object),
            listDetails: expect.any(Object),
            artist: expect.any(Object),
            client: expect.any(Object),
          })
        );
      });

      it('passing IFavoriteList should create a new form with FormGroup', () => {
        const formGroup = service.createFavoriteListFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            picture: expect.any(Object),
            listDetails: expect.any(Object),
            artist: expect.any(Object),
            client: expect.any(Object),
          })
        );
      });
    });

    describe('getFavoriteList', () => {
      it('should return NewFavoriteList for default FavoriteList initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFavoriteListFormGroup(sampleWithNewData);

        const favoriteList = service.getFavoriteList(formGroup) as any;

        expect(favoriteList).toMatchObject(sampleWithNewData);
      });

      it('should return NewFavoriteList for empty FavoriteList initial value', () => {
        const formGroup = service.createFavoriteListFormGroup();

        const favoriteList = service.getFavoriteList(formGroup) as any;

        expect(favoriteList).toMatchObject({});
      });

      it('should return IFavoriteList', () => {
        const formGroup = service.createFavoriteListFormGroup(sampleWithRequiredData);

        const favoriteList = service.getFavoriteList(formGroup) as any;

        expect(favoriteList).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFavoriteList should not enable id FormControl', () => {
        const formGroup = service.createFavoriteListFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFavoriteList should disable id FormControl', () => {
        const formGroup = service.createFavoriteListFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
