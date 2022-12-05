import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventario } from 'app/shared/model/inventario.model';

@Component({
  selector: 'jhi-inventario-detail',
  templateUrl: './inventario-detail.component.html'
})
export class InventarioDetailComponent implements OnInit {
  inventario: IInventario;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inventario }) => {
      this.inventario = inventario;
    });
  }

  previousState() {
    window.history.back();
  }
}
