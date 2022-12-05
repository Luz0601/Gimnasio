import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';

@Component({
  selector: 'jhi-cliente-suscripcion-detail',
  templateUrl: './cliente-suscripcion-detail.component.html'
})
export class ClienteSuscripcionDetailComponent implements OnInit {
  clienteSuscripcion: IClienteSuscripcion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clienteSuscripcion }) => {
      this.clienteSuscripcion = clienteSuscripcion;
    });
  }

  previousState() {
    window.history.back();
  }
}
