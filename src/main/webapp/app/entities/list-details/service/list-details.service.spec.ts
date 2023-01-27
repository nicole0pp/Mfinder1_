import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IListDetails } from '../list-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../list-details.test-samples';

import { ListDetailsService } from './list-details.service';

const requireRestSample: IListDetails = {
  ...sampleWithRequiredData,
};

describe('ListDetails Service', () => {
  let service: ListDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IListDetails | IListDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ListDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ListDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const listDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(listDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ListDetails', () => {
      const listDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(listDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ListDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ListDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ListDetails', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addListDetailsToCollectionIfMissing', () => {
      it('should add a ListDetails to an empty array', () => {
        const listDetails: IListDetails = sampleWithRequiredData;
        expectedResult = service.addListDetailsToCollectionIfMissing([], listDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(listDetails);
      });

      it('should not add a ListDetails to an array that contains it', () => {
        const listDetails: IListDetails = sampleWithRequiredData;
        const listDetailsCollection: IListDetails[] = [
          {
            ...listDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addListDetailsToCollectionIfMissing(listDetailsCollection, listDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ListDetails to an array that doesn't contain it", () => {
        const listDetails: IListDetails = sampleWithRequiredData;
        const listDetailsCollection: IListDetails[] = [sampleWithPartialData];
        expectedResult = service.addListDetailsToCollectionIfMissing(listDetailsCollection, listDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(listDetails);
      });

      it('should add only unique ListDetails to an array', () => {
        const listDetailsArray: IListDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const listDetailsCollection: IListDetails[] = [sampleWithRequiredData];
        expectedResult = service.addListDetailsToCollectionIfMissing(listDetailsCollection, ...listDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const listDetails: IListDetails = sampleWithRequiredData;
        const listDetails2: IListDetails = sampleWithPartialData;
        expectedResult = service.addListDetailsToCollectionIfMissing([], listDetails, listDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(listDetails);
        expect(expectedResult).toContain(listDetails2);
      });

      it('should accept null and undefined values', () => {
        const listDetails: IListDetails = sampleWithRequiredData;
        expectedResult = service.addListDetailsToCollectionIfMissing([], null, listDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(listDetails);
      });

      it('should return initial array if no ListDetails is added', () => {
        const listDetailsCollection: IListDetails[] = [sampleWithRequiredData];
        expectedResult = service.addListDetailsToCollectionIfMissing(listDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(listDetailsCollection);
      });
    });

    describe('compareListDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareListDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareListDetails(entity1, entity2);
        const compareResult2 = service.compareListDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareListDetails(entity1, entity2);
        const compareResult2 = service.compareListDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareListDetails(entity1, entity2);
        const compareResult2 = service.compareListDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
