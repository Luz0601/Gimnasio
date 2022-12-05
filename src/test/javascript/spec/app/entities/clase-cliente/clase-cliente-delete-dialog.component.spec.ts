/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { ClaseClienteDeleteDialogComponent } from 'app/entities/clase-cliente/clase-cliente-delete-dialog.component';
import { ClaseClienteService } from 'app/entities/clase-cliente/clase-cliente.service';

describe('Component Tests', () => {
  describe('ClaseCliente Management Delete Component', () => {
    let comp: ClaseClienteDeleteDialogComponent;
    let fixture: ComponentFixture<ClaseClienteDeleteDialogComponent>;
    let service: ClaseClienteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClaseClienteDeleteDialogComponent]
      })
        .overrideTemplate(ClaseClienteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClaseClienteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClaseClienteService);
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
