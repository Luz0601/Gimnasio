import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClase } from 'app/shared/model/clase.model';
import { ClaseService } from './clase.service';

@Component({
  selector: 'jhi-clase-delete-dialog',
  templateUrl: './clase-delete-dialog.component.html'
})
export class ClaseDeleteDialogComponent {
  clase: IClase;

  constructor(protected claseService: ClaseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.claseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'claseListModification',
        content: 'Deleted an clase'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-clase-delete-popup',
  template: ''
})
export class ClaseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clase }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClaseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.clase = clase;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/clase', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/clase', { outlets: { popup: null } }]);
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
