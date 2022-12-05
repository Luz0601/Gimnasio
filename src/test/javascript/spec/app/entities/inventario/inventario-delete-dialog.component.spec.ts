/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { InventarioDeleteDialogComponent } from 'app/entities/inventario/inventario-delete-dialog.component';
import { InventarioService } from 'app/entities/inventario/inventario.service';

describe('Component Tests', () => {
  describe('Inventario Management Delete Component', () => {
    let comp: InventarioDeleteDialogComponent;
    let fixture: ComponentFixture<InventarioDeleteDialogComponent>;
    let service: InventarioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [InventarioDeleteDialogComponent]
      })
        .overrideTemplate(InventarioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventarioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventarioService);
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
