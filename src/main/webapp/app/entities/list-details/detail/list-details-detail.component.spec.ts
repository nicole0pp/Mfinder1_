import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ListDetailsDetailComponent } from './list-details-detail.component';

describe('ListDetails Management Detail Component', () => {
  let comp: ListDetailsDetailComponent;
  let fixture: ComponentFixture<ListDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ listDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ListDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ListDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load listDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.listDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
