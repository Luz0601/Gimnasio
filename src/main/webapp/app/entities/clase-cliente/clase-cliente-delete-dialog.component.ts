import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClaseCliente } from 'app/shared/model/clase-cliente.model';
import { ClaseClienteService } from './clase-cliente.service';

@Component({
  selector: 'jhi-clase-cliente-delete-dialog',
  templateUrl: './clase-cliente-delete-dialog.component.html'
})
export class ClaseClienteDeleteDialogComponent {
  claseCliente: IClaseCliente;

  constructor(
    protected claseClienteService: ClaseClienteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.claseClienteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'claseClienteListModification',
        content: 'Deleted an claseCliente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-clase-cliente-delete-popup',
  template: ''
})
export class ClaseClienteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ claseCliente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClaseClienteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.claseCliente = claseCliente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/clase-cliente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/clase-cliente', { outlets: { popup: null } }]);
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
