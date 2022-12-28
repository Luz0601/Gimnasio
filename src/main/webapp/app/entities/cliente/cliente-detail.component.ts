import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICliente } from 'app/shared/model/cliente.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClienteUpdateComponent } from './cliente-update.component';

@Component({
  selector: 'jhi-cliente-detail',
  templateUrl: './cliente-detail.component.html'
})
export class ClienteDetailComponent implements OnInit {
  cliente: ICliente;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal, protected modalService: NgbModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(ClienteUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.cliente = content;
  }
}
