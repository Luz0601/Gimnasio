import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuesto } from 'app/shared/model/puesto.model';

@Component({
  selector: 'jhi-puesto-detail',
  templateUrl: './puesto-detail.component.html'
})
export class PuestoDetailComponent implements OnInit {
  puesto: IPuesto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ puesto }) => {
      this.puesto = puesto;
    });
  }

  previousState() {
    window.history.back();
  }
}
