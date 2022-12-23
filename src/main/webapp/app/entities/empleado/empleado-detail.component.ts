import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NominaDetailComponent, NominaService } from '../nomina';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { INomina } from 'app/shared/model/nomina.model';
import { JhiAlertService } from 'ng-jhipster';
import { EmpleadoUpdateComponent } from './empleado-update.component';

@Component({
  selector: 'jhi-empleado-detail',
  templateUrl: './empleado-detail.component.html'
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: IEmpleado;

  constructor(
    protected nominaService: NominaService,
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected modal: NgbActiveModal,
    private modalService: NgbModal
  ) {}

  ngOnInit() {}

  previousState() {
    window.history.back();
  }

  nomina(content) {
    this.nominaService.find(content).subscribe(
      (res: HttpResponse<INomina>) => {
        const modalRef = this.modalService.open(NominaDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
        modalRef.componentInstance.nomina = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  editar(content) {
    const modalRef = this.modalService.open(EmpleadoUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.empleado = content;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
