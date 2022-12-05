/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { InventarioUpdateComponent } from 'app/entities/inventario/inventario-update.component';
import { InventarioService } from 'app/entities/inventario/inventario.service';
import { Inventario } from 'app/shared/model/inventario.model';

describe('Component Tests', () => {
  describe('Inventario Management Update Component', () => {
    let comp: InventarioUpdateComponent;
    let fixture: ComponentFixture<InventarioUpdateComponent>;
    let service: InventarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [InventarioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InventarioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventarioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventarioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inventario(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inventario();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
