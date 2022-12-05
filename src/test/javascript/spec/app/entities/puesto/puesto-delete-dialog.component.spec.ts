/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { PuestoDeleteDialogComponent } from 'app/entities/puesto/puesto-delete-dialog.component';
import { PuestoService } from 'app/entities/puesto/puesto.service';

describe('Component Tests', () => {
  describe('Puesto Management Delete Component', () => {
    let comp: PuestoDeleteDialogComponent;
    let fixture: ComponentFixture<PuestoDeleteDialogComponent>;
    let service: PuestoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [PuestoDeleteDialogComponent]
      })
        .overrideTemplate(PuestoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PuestoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PuestoService);
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
