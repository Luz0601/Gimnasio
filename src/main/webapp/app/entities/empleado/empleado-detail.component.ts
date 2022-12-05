import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpleado } from 'app/shared/model/empleado.model';

@Component({
  selector: 'jhi-empleado-detail',
  templateUrl: './empleado-detail.component.html'
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: IEmpleado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.empleado = empleado;
    });
  }

  previousState() {
    window.history.back();
  }
}
