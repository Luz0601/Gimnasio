import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVacaciones } from 'app/shared/model/vacaciones.model';
import { VacacionesService } from './vacaciones.service';

@Component({
  selector: 'jhi-vacaciones-delete-dialog',
  templateUrl: './vacaciones-delete-dialog.component.html'
})
export class VacacionesDeleteDialogComponent {
  vacaciones: IVacaciones;

  constructor(
    protected vacacionesService: VacacionesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vacacionesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vacacionesListModification',
        content: 'Deleted an vacaciones'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vacaciones-delete-popup',
  template: ''
})
export class VacacionesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vacaciones }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VacacionesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vacaciones = vacaciones;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vacaciones', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vacaciones', { outlets: { popup: null } }]);
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
