import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventario } from 'app/shared/model/inventario.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InventarioUpdateComponent } from './inventario-update.component';
import { ProveedorDetailComponent, ProveedorService } from '../proveedor';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { JhiAlertService } from 'ng-jhipster';

@Component({
  selector: 'jhi-inventario-detail',
  templateUrl: './inventario-detail.component.html'
})
export class InventarioDetailComponent implements OnInit {
  inventario: IInventario;

  constructor(
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected modalService: NgbModal,
    protected modal: NgbActiveModal
  ) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(InventarioUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.inventario = content;
  }

  proveedor(content) {
    this.proveedorService.find(content).subscribe(
      (res: HttpResponse<IProveedor>) => {
        const modalRef = this.modalService.open(ProveedorDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
        modalRef.componentInstance.proveedor = res.body;
      },
      (res: HttpErrorResponse) => this.jhiAlertService.error(res.message, null, null)
    );
  }
}
