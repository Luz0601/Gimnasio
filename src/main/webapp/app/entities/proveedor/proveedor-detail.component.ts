import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorUpdateComponent } from './proveedor-update.component';

@Component({
  selector: 'jhi-proveedor-detail',
  templateUrl: './proveedor-detail.component.html'
})
export class ProveedorDetailComponent implements OnInit {
  proveedor: IProveedor;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(ProveedorUpdateComponent, { ariaLabelledBy: 'modal-baic-title' });
    modalRef.componentInstance.proveedor = content;
  }
}
