/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GimnasioTestModule } from '../../../test.module';
import { IncidenciaDeleteDialogComponent } from 'app/entities/incidencia/incidencia-delete-dialog.component';
import { IncidenciaService } from 'app/entities/incidencia/incidencia.service';

describe('Component Tests', () => {
  describe('Incidencia Management Delete Component', () => {
    let comp: IncidenciaDeleteDialogComponent;
    let fixture: ComponentFixture<IncidenciaDeleteDialogComponent>;
    let service: IncidenciaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [IncidenciaDeleteDialogComponent]
      })
        .overrideTemplate(IncidenciaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidenciaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidenciaService);
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
