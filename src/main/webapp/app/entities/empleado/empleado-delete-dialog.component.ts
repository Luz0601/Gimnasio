import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';

@Component({
  selector: 'jhi-empleado-delete-dialog',
  templateUrl: './empleado-delete-dialog.component.html'
})
export class EmpleadoDeleteDialogComponent {
  empleado: IEmpleado;

  constructor(protected empleadoService: EmpleadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.empleadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'empleadoListModification',
        content: 'Deleted an empleado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-empleado-delete-popup',
  template: ''
})
export class EmpleadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmpleadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.empleado = empleado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/empleado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/empleado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
