import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFavoriteList } from '../favorite-list.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../favorite-list.test-samples';

import { FavoriteListService } from './favorite-list.service';

const requireRestSample: IFavoriteList = {
  ...sampleWithRequiredData,
};

describe('FavoriteList Service', () => {
  let service: FavoriteListService;
  let httpMock: HttpTestingController;
  let expectedResult: IFavoriteList | IFavoriteList[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FavoriteListService);
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

    it('should create a FavoriteList', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const favoriteList = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(favoriteList).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FavoriteList', () => {
      const favoriteList = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(favoriteList).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FavoriteList', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FavoriteList', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FavoriteList', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFavoriteListToCollectionIfMissing', () => {
      it('should add a FavoriteList to an empty array', () => {
        const favoriteList: IFavoriteList = sampleWithRequiredData;
        expectedResult = service.addFavoriteListToCollectionIfMissing([], favoriteList);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(favoriteList);
      });

      it('should not add a FavoriteList to an array that contains it', () => {
        const favoriteList: IFavoriteList = sampleWithRequiredData;
        const favoriteListCollection: IFavoriteList[] = [
          {
            ...favoriteList,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFavoriteListToCollectionIfMissing(favoriteListCollection, favoriteList);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FavoriteList to an array that doesn't contain it", () => {
        const favoriteList: IFavoriteList = sampleWithRequiredData;
        const favoriteListCollection: IFavoriteList[] = [sampleWithPartialData];
        expectedResult = service.addFavoriteListToCollectionIfMissing(favoriteListCollection, favoriteList);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(favoriteList);
      });

      it('should add only unique FavoriteList to an array', () => {
        const favoriteListArray: IFavoriteList[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const favoriteListCollection: IFavoriteList[] = [sampleWithRequiredData];
        expectedResult = service.addFavoriteListToCollectionIfMissing(favoriteListCollection, ...favoriteListArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const favoriteList: IFavoriteList = sampleWithRequiredData;
        const favoriteList2: IFavoriteList = sampleWithPartialData;
        expectedResult = service.addFavoriteListToCollectionIfMissing([], favoriteList, favoriteList2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(favoriteList);
        expect(expectedResult).toContain(favoriteList2);
      });

      it('should accept null and undefined values', () => {
        const favoriteList: IFavoriteList = sampleWithRequiredData;
        expectedResult = service.addFavoriteListToCollectionIfMissing([], null, favoriteList, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(favoriteList);
      });

      it('should return initial array if no FavoriteList is added', () => {
        const favoriteListCollection: IFavoriteList[] = [sampleWithRequiredData];
        expectedResult = service.addFavoriteListToCollectionIfMissing(favoriteListCollection, undefined, null);
        expect(expectedResult).toEqual(favoriteListCollection);
      });
    });

    describe('compareFavoriteList', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFavoriteList(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFavoriteList(entity1, entity2);
        const compareResult2 = service.compareFavoriteList(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFavoriteList(entity1, entity2);
        const compareResult2 = service.compareFavoriteList(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFavoriteList(entity1, entity2);
        const compareResult2 = service.compareFavoriteList(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
