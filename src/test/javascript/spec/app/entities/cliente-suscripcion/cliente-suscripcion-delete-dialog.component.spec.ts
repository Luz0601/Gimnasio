/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { ClienteSuscripcionDeleteDialogComponent } from 'app/entities/cliente-suscripcion/cliente-suscripcion-delete-dialog.component';
import { ClienteSuscripcionService } from 'app/entities/cliente-suscripcion/cliente-suscripcion.service';

describe('Component Tests', () => {
  describe('ClienteSuscripcion Management Delete Component', () => {
    let comp: ClienteSuscripcionDeleteDialogComponent;
    let fixture: ComponentFixture<ClienteSuscripcionDeleteDialogComponent>;
    let service: ClienteSuscripcionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClienteSuscripcionDeleteDialogComponent]
      })
        .overrideTemplate(ClienteSuscripcionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteSuscripcionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClienteSuscripcionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
