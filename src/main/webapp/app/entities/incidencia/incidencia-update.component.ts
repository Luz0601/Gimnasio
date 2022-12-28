import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncidencia, Incidencia } from 'app/shared/model/incidencia.model';
import { IncidenciaService } from './incidencia.service';
import { IClase } from 'app/shared/model/clase.model';
import { ClaseService } from 'app/entities/clase';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-incidencia-update',
  templateUrl: './incidencia-update.component.html'
})
export class IncidenciaUpdateComponent implements OnInit {
  incidencia: IIncidencia;
  isSaving: boolean;

  clases: IClase[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    claseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incidenciaService: IncidenciaService,
    protected claseService: ClaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected modal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.updateForm(this.incidencia);
    this.claseService
      .query({ filter: 'incidencia-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IClase[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClase[]>) => response.body)
      )
      .subscribe((res: IClase[]) => (this.clases = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(incidencia: IIncidencia) {
    this.editForm.patchValue({
      id: incidencia.id,
      nombre: incidencia.nombre,
      descripcion: incidencia.descripcion,
      claseId: incidencia.claseId
    });
  }

  previousState() {
    window.history.back();
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
    window.location.reload();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClaseById(index: number, item: IClase) {
    return item.id;
  }
}
