/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ClaseDetailComponent } from 'app/entities/clase/clase-detail.component';
import { Clase } from 'app/shared/model/clase.model';

describe('Component Tests', () => {
  describe('Clase Management Detail Component', () => {
    let comp: ClaseDetailComponent;
    let fixture: ComponentFixture<ClaseDetailComponent>;
    const route = ({ data: of({ clase: new Clase(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClaseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClaseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClaseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clase).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
