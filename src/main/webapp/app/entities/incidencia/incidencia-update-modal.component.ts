import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { IClase } from 'app/shared/model/clase.model';
import { IIncidencia, Incidencia } from 'app/shared/model/incidencia.model';
import { JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { IncidenciaService } from './incidencia.service';

@Component({
  selector: 'jhi-incidencia-update-modal',
  templateUrl: './incidencia-update-modal.component.html'
})
export class IncidenciaUpdateModalComponent implements OnInit {
  incidencia: IIncidencia;
  isSaving: boolean;

  @Input() clase: IClase;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    claseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incidenciaService: IncidenciaService,
    protected activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}
  ngOnInit() {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  save() {
    this.isSaving = true;
    const incidencia = this.createFromForm();
    if (incidencia.id !== undefined) {
      this.subscribeToSaveResponse(this.incidenciaService.update(incidencia));
    } else {
      this.subscribeToSaveResponse(this.incidenciaService.create(incidencia));
    }
  }

  private createFromForm(): IIncidencia {
    const entity = {
      ...new Incidencia(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      claseId: this.editForm.get(['claseId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidencia>>) {
    result.subscribe((res: HttpResponse<IIncidencia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.clear();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
