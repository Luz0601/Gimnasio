/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { NominaDetailComponent } from 'app/entities/nomina/nomina-detail.component';
import { Nomina } from 'app/shared/model/nomina.model';

describe('Component Tests', () => {
  describe('Nomina Management Detail Component', () => {
    let comp: NominaDetailComponent;
    let fixture: ComponentFixture<NominaDetailComponent>;
    const route = ({ data: of({ nomina: new Nomina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [NominaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NominaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NominaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nomina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
