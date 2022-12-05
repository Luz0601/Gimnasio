/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { VacacionesUpdateComponent } from 'app/entities/vacaciones/vacaciones-update.component';
import { VacacionesService } from 'app/entities/vacaciones/vacaciones.service';
import { Vacaciones } from 'app/shared/model/vacaciones.model';

describe('Component Tests', () => {
  describe('Vacaciones Management Update Component', () => {
    let comp: VacacionesUpdateComponent;
    let fixture: ComponentFixture<VacacionesUpdateComponent>;
    let service: VacacionesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [VacacionesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VacacionesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VacacionesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VacacionesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vacaciones(123);
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
        const entity = new Vacaciones();
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
