import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';
import { ClienteSuscripcionService } from './cliente-suscripcion.service';

@Component({
  selector: 'jhi-cliente-suscripcion-delete-dialog',
  templateUrl: './cliente-suscripcion-delete-dialog.component.html'
})
export class ClienteSuscripcionDeleteDialogComponent {
  clienteSuscripcion: IClienteSuscripcion;

  constructor(
    protected clienteSuscripcionService: ClienteSuscripcionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clienteSuscripcionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'clienteSuscripcionListModification',
        content: 'Deleted an clienteSuscripcion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cliente-suscripcion-delete-popup',
  template: ''
})
export class ClienteSuscripcionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clienteSuscripcion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClienteSuscripcionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.clienteSuscripcion = clienteSuscripcion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cliente-suscripcion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cliente-suscripcion', { outlets: { popup: null } }]);
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
