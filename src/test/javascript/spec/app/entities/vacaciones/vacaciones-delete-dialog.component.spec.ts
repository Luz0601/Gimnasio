/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { VacacionesDeleteDialogComponent } from 'app/entities/vacaciones/vacaciones-delete-dialog.component';
import { VacacionesService } from 'app/entities/vacaciones/vacaciones.service';

describe('Component Tests', () => {
  describe('Vacaciones Management Delete Component', () => {
    let comp: VacacionesDeleteDialogComponent;
    let fixture: ComponentFixture<VacacionesDeleteDialogComponent>;
    let service: VacacionesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [VacacionesDeleteDialogComponent]
      })
        .overrideTemplate(VacacionesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VacacionesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VacacionesService);
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
