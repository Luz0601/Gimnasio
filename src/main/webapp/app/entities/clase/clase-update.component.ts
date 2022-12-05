import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IClase, Clase } from 'app/shared/model/clase.model';
import { ClaseService } from './clase.service';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';

@Component({
  selector: 'jhi-clase-update',
  templateUrl: './clase-update.component.html'
})
export class ClaseUpdateComponent implements OnInit {
  clase: IClase;
  isSaving: boolean;

  monitors: IEmpleado[];
  inicioDp: any;
  finDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [],
    lugar: [],
    inicio: [null, [Validators.required]],
    fin: [null, [Validators.required]],
    incidencias: [],
    monitorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected claseService: ClaseService,
    protected empleadoService: EmpleadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clase }) => {
      this.updateForm(clase);
      this.clase = clase;
    });
    this.empleadoService
      .query({ filter: 'clase-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpleado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpleado[]>) => response.body)
      )
      .subscribe(
        (res: IEmpleado[]) => {
          if (!this.clase.monitorId) {
            this.monitors = res;
          } else {
            this.empleadoService
              .find(this.clase.monitorId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEmpleado>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEmpleado>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEmpleado) => (this.monitors = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(clase: IClase) {
    this.editForm.patchValue({
      id: clase.id,
      nombre: clase.nombre,
      descripcion: clase.descripcion,
      lugar: clase.lugar,
      inicio: clase.inicio,
      fin: clase.fin,
      incidencias: clase.incidencias,
      monitorId: clase.monitorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clase = this.createFromForm();
    if (clase.id !== undefined) {
      this.subscribeToSaveResponse(this.claseService.update(clase));
    } else {
      this.subscribeToSaveResponse(this.claseService.create(clase));
    }
  }

  private createFromForm(): IClase {
    const entity = {
      ...new Clase(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      lugar: this.editForm.get(['lugar']).value,
      inicio: this.editForm.get(['inicio']).value,
      fin: this.editForm.get(['fin']).value,
      incidencias: this.editForm.get(['incidencias']).value,
      monitorId: this.editForm.get(['monitorId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClase>>) {
    result.subscribe((res: HttpResponse<IClase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEmpleadoById(index: number, item: IEmpleado) {
    return item.id;
  }
}
