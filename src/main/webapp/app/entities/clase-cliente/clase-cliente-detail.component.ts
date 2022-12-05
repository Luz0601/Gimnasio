import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClaseCliente } from 'app/shared/model/clase-cliente.model';

@Component({
  selector: 'jhi-clase-cliente-detail',
  templateUrl: './clase-cliente-detail.component.html'
})
export class ClaseClienteDetailComponent implements OnInit {
  claseCliente: IClaseCliente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ claseCliente }) => {
      this.claseCliente = claseCliente;
    });
  }

  previousState() {
    window.history.back();
  }
}
