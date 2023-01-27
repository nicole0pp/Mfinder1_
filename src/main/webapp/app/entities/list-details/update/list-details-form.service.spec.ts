import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../list-details.test-samples';

import { ListDetailsFormService } from './list-details-form.service';

describe('ListDetails Form Service', () => {
  let service: ListDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createListDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createListDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
          })
        );
      });

      it('passing IListDetails should create a new form with FormGroup', () => {
        const formGroup = service.createListDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
          })
        );
      });
    });

    describe('getListDetails', () => {
      it('should return NewListDetails for default ListDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createListDetailsFormGroup(sampleWithNewData);

        const listDetails = service.getListDetails(formGroup) as any;

        expect(listDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewListDetails for empty ListDetails initial value', () => {
        const formGroup = service.createListDetailsFormGroup();

        const listDetails = service.getListDetails(formGroup) as any;

        expect(listDetails).toMatchObject({});
      });

      it('should return IListDetails', () => {
        const formGroup = service.createListDetailsFormGroup(sampleWithRequiredData);

        const listDetails = service.getListDetails(formGroup) as any;

        expect(listDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IListDetails should not enable id FormControl', () => {
        const formGroup = service.createListDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewListDetails should disable id FormControl', () => {
        const formGroup = service.createListDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
