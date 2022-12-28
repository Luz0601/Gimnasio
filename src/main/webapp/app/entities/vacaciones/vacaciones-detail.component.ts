import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVacaciones } from 'app/shared/model/vacaciones.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VacacionesUpdateComponent } from './vacaciones-update.component';

@Component({
  selector: 'jhi-vacaciones-detail',
  templateUrl: './vacaciones-detail.component.html'
})
export class VacacionesDetailComponent implements OnInit {
  vacaciones: IVacaciones;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(VacacionesUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.vacaciones = content;
  }
}
