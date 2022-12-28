import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClase } from 'app/shared/model/clase.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClaseUpdateComponent } from './clase-update.component';
import { IncidenciaDetailComponent } from '../incidencia';

@Component({
  selector: 'jhi-clase-detail',
  templateUrl: './clase-detail.component.html'
})
export class ClaseDetailComponent implements OnInit {
  clase: IClase;
  edit: Boolean;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(ClaseUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.clase = content;
  }

  incidencia(content) {
    const modalRef = this.modalService.open(IncidenciaDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.incidencia = content;
  }
}
