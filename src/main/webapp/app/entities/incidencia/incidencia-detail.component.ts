import { Component, OnInit } from '@angular/core';

import { IIncidencia } from 'app/shared/model/incidencia.model';

@Component({
  selector: 'jhi-incidencia-detail',
  templateUrl: './incidencia-detail.component.html'
})
export class IncidenciaDetailComponent implements OnInit {
  incidencia: IIncidencia;

  constructor() {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
