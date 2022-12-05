import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClase } from 'app/shared/model/clase.model';

@Component({
  selector: 'jhi-clase-detail',
  templateUrl: './clase-detail.component.html'
})
export class ClaseDetailComponent implements OnInit {
  clase: IClase;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clase }) => {
      this.clase = clase;
    });
  }

  previousState() {
    window.history.back();
  }
}
