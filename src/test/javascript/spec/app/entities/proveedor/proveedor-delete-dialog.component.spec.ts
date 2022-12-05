/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { ProveedorDeleteDialogComponent } from 'app/entities/proveedor/proveedor-delete-dialog.component';
import { ProveedorService } from 'app/entities/proveedor/proveedor.service';

describe('Component Tests', () => {
  describe('Proveedor Management Delete Component', () => {
    let comp: ProveedorDeleteDialogComponent;
    let fixture: ComponentFixture<ProveedorDeleteDialogComponent>;
    let service: ProveedorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ProveedorDeleteDialogComponent]
      })
        .overrideTemplate(ProveedorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProveedorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProveedorService);
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
