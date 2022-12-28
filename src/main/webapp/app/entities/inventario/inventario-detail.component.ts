import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventario } from 'app/shared/model/inventario.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InventarioUpdateComponent } from './inventario-update.component';

@Component({
  selector: 'jhi-inventario-detail',
  templateUrl: './inventario-detail.component.html'
})
export class InventarioDetailComponent implements OnInit {
  inventario: IInventario;

  constructor(protected activatedRoute: ActivatedRoute, protected modalService: NgbModal, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(InventarioUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.inventario = content;
  }
}
