import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-empleado-detail',
  templateUrl: './empleado-detail.component.html'
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: IEmpleado;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
