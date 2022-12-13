import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClase } from 'app/shared/model/clase.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-clase-detail',
  templateUrl: './clase-detail.component.html'
})
export class ClaseDetailComponent implements OnInit {
  clase: IClase;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
