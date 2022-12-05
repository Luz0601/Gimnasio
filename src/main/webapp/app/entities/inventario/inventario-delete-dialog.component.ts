import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';

@Component({
  selector: 'jhi-inventario-delete-dialog',
  templateUrl: './inventario-delete-dialog.component.html'
})
export class InventarioDeleteDialogComponent {
  inventario: IInventario;

  constructor(
    protected inventarioService: InventarioService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inventarioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inventarioListModification',
        content: 'Deleted an inventario'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inventario-delete-popup',
  template: ''
})
export class InventarioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventario }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InventarioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inventario = inventario;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inventario', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inventario', { outlets: { popup: null } }]);
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
