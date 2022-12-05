/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { EmpleadoUpdateComponent } from 'app/entities/empleado/empleado-update.component';
import { EmpleadoService } from 'app/entities/empleado/empleado.service';
import { Empleado } from 'app/shared/model/empleado.model';

describe('Component Tests', () => {
  describe('Empleado Management Update Component', () => {
    let comp: EmpleadoUpdateComponent;
    let fixture: ComponentFixture<EmpleadoUpdateComponent>;
    let service: EmpleadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [EmpleadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmpleadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpleadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpleadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Empleado(123);
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
        const entity = new Empleado();
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
