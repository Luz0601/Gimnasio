/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { VacacionesDetailComponent } from 'app/entities/vacaciones/vacaciones-detail.component';
import { Vacaciones } from 'app/shared/model/vacaciones.model';

describe('Component Tests', () => {
  describe('Vacaciones Management Detail Component', () => {
    let comp: VacacionesDetailComponent;
    let fixture: ComponentFixture<VacacionesDetailComponent>;
    const route = ({ data: of({ vacaciones: new Vacaciones(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [VacacionesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VacacionesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VacacionesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vacaciones).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
