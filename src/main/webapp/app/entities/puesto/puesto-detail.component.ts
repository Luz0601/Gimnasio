import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuesto } from 'app/shared/model/puesto.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-puesto-detail',
  templateUrl: './puesto-detail.component.html'
})
export class PuestoDetailComponent implements OnInit {
  puesto: IPuesto;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }
}
