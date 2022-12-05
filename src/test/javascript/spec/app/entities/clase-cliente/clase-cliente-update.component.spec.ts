/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ClaseClienteUpdateComponent } from 'app/entities/clase-cliente/clase-cliente-update.component';
import { ClaseClienteService } from 'app/entities/clase-cliente/clase-cliente.service';
import { ClaseCliente } from 'app/shared/model/clase-cliente.model';

describe('Component Tests', () => {
  describe('ClaseCliente Management Update Component', () => {
    let comp: ClaseClienteUpdateComponent;
    let fixture: ComponentFixture<ClaseClienteUpdateComponent>;
    let service: ClaseClienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClaseClienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClaseClienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClaseClienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClaseClienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClaseCliente(123);
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
        const entity = new ClaseCliente();
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
