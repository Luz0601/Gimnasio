import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVacaciones } from 'app/shared/model/vacaciones.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-vacaciones-detail',
  templateUrl: './vacaciones-detail.component.html'
})
export class VacacionesDetailComponent implements OnInit {
  vacaciones: IVacaciones;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
