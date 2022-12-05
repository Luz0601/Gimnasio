/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { IncidenciaUpdateComponent } from 'app/entities/incidencia/incidencia-update.component';
import { IncidenciaService } from 'app/entities/incidencia/incidencia.service';
import { Incidencia } from 'app/shared/model/incidencia.model';

describe('Component Tests', () => {
  describe('Incidencia Management Update Component', () => {
    let comp: IncidenciaUpdateComponent;
    let fixture: ComponentFixture<IncidenciaUpdateComponent>;
    let service: IncidenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [IncidenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IncidenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncidenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Incidencia(123);
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
        const entity = new Incidencia();
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
