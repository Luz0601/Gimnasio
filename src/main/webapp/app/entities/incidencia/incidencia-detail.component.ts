import { Component, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncidencia } from 'app/shared/model/incidencia.model';
import { IncidenciaUpdateComponent } from './incidencia-update.component';

@Component({
  selector: 'jhi-incidencia-detail',
  templateUrl: './incidencia-detail.component.html'
})
export class IncidenciaDetailComponent implements OnInit {
  incidencia: IIncidencia;

  constructor(protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(IncidenciaUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.incidencia = content;
  }
}
