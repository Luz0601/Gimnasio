import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProveedor } from 'app/shared/model/proveedor.model';

@Component({
  selector: 'jhi-proveedor-detail',
  templateUrl: './proveedor-detail.component.html'
})
export class ProveedorDetailComponent implements OnInit {
  proveedor: IProveedor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.proveedor = proveedor;
    });
  }

  previousState() {
    window.history.back();
  }
}
