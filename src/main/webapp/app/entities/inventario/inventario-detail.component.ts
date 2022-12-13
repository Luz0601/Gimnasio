import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventario } from 'app/shared/model/inventario.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-inventario-detail',
  templateUrl: './inventario-detail.component.html'
})
export class InventarioDetailComponent implements OnInit {
  inventario: IInventario;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
