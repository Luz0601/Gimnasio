import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INomina } from 'app/shared/model/nomina.model';
import { JhiAlertService } from 'ng-jhipster';
import { NominaUpdateComponent } from './nomina-update.component';

@Component({
  selector: 'jhi-nomina-detail',
  templateUrl: './nomina-detail.component.html'
})
export class NominaDetailComponent implements OnInit {
  nomina: INomina;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected modal: NgbActiveModal,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    // this.activatedRoute.data.subscribe(({ nomina }) => {
    // this.nomina = nomina;
    // });
  }

  previousState() {
    window.history.back();
  }

  editar(content) {
    const modalRef = this.modalService.open(NominaUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.nomina = content;
  }
}
