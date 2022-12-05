import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVacaciones } from 'app/shared/model/vacaciones.model';

@Component({
  selector: 'jhi-vacaciones-detail',
  templateUrl: './vacaciones-detail.component.html'
})
export class VacacionesDetailComponent implements OnInit {
  vacaciones: IVacaciones;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vacaciones }) => {
      this.vacaciones = vacaciones;
    });
  }

  previousState() {
    window.history.back();
  }
}
