/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ClienteSuscripcionUpdateComponent } from 'app/entities/cliente-suscripcion/cliente-suscripcion-update.component';
import { ClienteSuscripcionService } from 'app/entities/cliente-suscripcion/cliente-suscripcion.service';
import { ClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';

describe('Component Tests', () => {
  describe('ClienteSuscripcion Management Update Component', () => {
    let comp: ClienteSuscripcionUpdateComponent;
    let fixture: ComponentFixture<ClienteSuscripcionUpdateComponent>;
    let service: ClienteSuscripcionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClienteSuscripcionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClienteSuscripcionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClienteSuscripcionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteSuscripcionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClienteSuscripcion(123);
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
        const entity = new ClienteSuscripcion();
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
