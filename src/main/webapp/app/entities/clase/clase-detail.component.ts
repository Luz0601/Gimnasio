import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClase } from 'app/shared/model/clase.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ClaseUpdateComponent } from './clase-update.component';

@Component({
  selector: 'jhi-clase-detail',
  templateUrl: './clase-detail.component.html'
})
export class ClaseDetailComponent implements OnInit {
  clase: IClase;

  constructor(protected activatedRoute: ActivatedRoute, protected modal: NgbActiveModal, private modalService: NgbModal) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  open(content) {
    // this.modal.close('Edit');
    const modalRef = this.modalService.open(ClaseUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.clase = content;
  }
}
