/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ClaseClienteDetailComponent } from 'app/entities/clase-cliente/clase-cliente-detail.component';
import { ClaseCliente } from 'app/shared/model/clase-cliente.model';

describe('Component Tests', () => {
  describe('ClaseCliente Management Detail Component', () => {
    let comp: ClaseClienteDetailComponent;
    let fixture: ComponentFixture<ClaseClienteDetailComponent>;
    const route = ({ data: of({ claseCliente: new ClaseCliente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClaseClienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClaseClienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClaseClienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.claseCliente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
