import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPuesto } from 'app/shared/model/puesto.model';
import { PuestoService } from './puesto.service';

@Component({
  selector: 'jhi-puesto-delete-dialog',
  templateUrl: './puesto-delete-dialog.component.html'
})
export class PuestoDeleteDialogComponent {
  puesto: IPuesto;

  constructor(protected puestoService: PuestoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.puestoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'puestoListModification',
        content: 'Deleted an puesto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-puesto-delete-popup',
  template: ''
})
export class PuestoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ puesto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PuestoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.puesto = puesto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/puesto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/puesto', { outlets: { popup: null } }]);
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
