import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INomina } from 'app/shared/model/nomina.model';
import { NominaService } from './nomina.service';

@Component({
  selector: 'jhi-nomina-delete-dialog',
  templateUrl: './nomina-delete-dialog.component.html'
})
export class NominaDeleteDialogComponent {
  nomina: INomina;

  constructor(protected nominaService: NominaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.nominaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'nominaListModification',
        content: 'Deleted an nomina'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nomina-delete-popup',
  template: ''
})
export class NominaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nomina }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NominaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.nomina = nomina;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nomina', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nomina', { outlets: { popup: null } }]);
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
