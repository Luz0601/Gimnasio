/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { InventarioDetailComponent } from 'app/entities/inventario/inventario-detail.component';
import { Inventario } from 'app/shared/model/inventario.model';

describe('Component Tests', () => {
  describe('Inventario Management Detail Component', () => {
    let comp: InventarioDetailComponent;
    let fixture: ComponentFixture<InventarioDetailComponent>;
    const route = ({ data: of({ inventario: new Inventario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [InventarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InventarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventarioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
