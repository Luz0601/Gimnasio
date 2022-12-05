/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { NominaUpdateComponent } from 'app/entities/nomina/nomina-update.component';
import { NominaService } from 'app/entities/nomina/nomina.service';
import { Nomina } from 'app/shared/model/nomina.model';

describe('Component Tests', () => {
  describe('Nomina Management Update Component', () => {
    let comp: NominaUpdateComponent;
    let fixture: ComponentFixture<NominaUpdateComponent>;
    let service: NominaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [NominaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NominaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NominaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NominaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Nomina(123);
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
        const entity = new Nomina();
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
