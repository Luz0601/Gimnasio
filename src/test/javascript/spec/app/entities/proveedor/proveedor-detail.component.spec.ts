/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ProveedorDetailComponent } from 'app/entities/proveedor/proveedor-detail.component';
import { Proveedor } from 'app/shared/model/proveedor.model';

describe('Component Tests', () => {
  describe('Proveedor Management Detail Component', () => {
    let comp: ProveedorDetailComponent;
    let fixture: ComponentFixture<ProveedorDetailComponent>;
    const route = ({ data: of({ proveedor: new Proveedor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ProveedorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProveedorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProveedorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proveedor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
