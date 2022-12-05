import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidencia } from 'app/shared/model/incidencia.model';
import { IncidenciaService } from './incidencia.service';

@Component({
  selector: 'jhi-incidencia-delete-dialog',
  templateUrl: './incidencia-delete-dialog.component.html'
})
export class IncidenciaDeleteDialogComponent {
  incidencia: IIncidencia;

  constructor(
    protected incidenciaService: IncidenciaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.incidenciaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'incidenciaListModification',
        content: 'Deleted an incidencia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-incidencia-delete-popup',
  template: ''
})
export class IncidenciaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ incidencia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IncidenciaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.incidencia = incidencia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/incidencia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/incidencia', { outlets: { popup: null } }]);
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
