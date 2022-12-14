import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INomina } from 'app/shared/model/nomina.model';

@Component({
  selector: 'jhi-nomina-detail',
  templateUrl: './nomina-detail.component.html'
})
export class NominaDetailComponent implements OnInit {
  nomina: INomina;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nomina }) => {
      this.nomina = nomina;
    });
  }

  previousState() {
    window.history.back();
  }
}
