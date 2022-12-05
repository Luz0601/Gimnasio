/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { PuestoUpdateComponent } from 'app/entities/puesto/puesto-update.component';
import { PuestoService } from 'app/entities/puesto/puesto.service';
import { Puesto } from 'app/shared/model/puesto.model';

describe('Component Tests', () => {
  describe('Puesto Management Update Component', () => {
    let comp: PuestoUpdateComponent;
    let fixture: ComponentFixture<PuestoUpdateComponent>;
    let service: PuestoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [PuestoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PuestoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PuestoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuestoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Puesto(123);
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
        const entity = new Puesto();
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
