import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuesto } from 'app/shared/model/puesto.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PuestoUpdateComponent } from './puesto-update.component';

@Component({
  selector: 'jhi-puesto-detail',
  templateUrl: './puesto-detail.component.html'
})
export class PuestoDetailComponent implements OnInit {
  puesto: IPuesto;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(PuestoUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.puesto = content;
  }
}
