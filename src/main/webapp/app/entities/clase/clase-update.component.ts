import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IClase, Clase } from 'app/shared/model/clase.model';
import { ClaseService } from './clase.service';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';
import { IIncidencia, Incidencia } from 'app/shared/model/incidencia.model';

@Component({
  selector: 'jhi-clase-update',
  templateUrl: './clase-update.component.html'
})
export class ClaseUpdateComponent implements OnInit {
  clase: IClase;
  isSaving: boolean;

  monitors: IEmpleado[];
  inicioDp: any;
  horaInicio: any;
  horaFin: any;
  finDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    descripcion: [],
    lugar: [],
    inicio: [null, [Validators.required]],
    fin: [null, [Validators.required]],
    incidencias: [],
    monitorId: [null, [Validators.required]],
    incidencia: [],
    horaInicio: [null, [Validators.required]],
    horaFin: [null, [Validators.required]]
  });

  incidenciaForm = this.fb.group({
    nombre: [null, [Validators.required]],
    descripcion: [null, [Validators.required]]
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
    if (!this.clase) {
      this.clase = new Clase();
      if (!this.clase.incidencias) {
        this.clase.incidencias = false;
      }
    }
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
    if (clase.inicio) this.horaInicio = { hour: clase.inicio.hours(), minute: clase.inicio.minutes() };
    if (clase.fin) this.horaFin = { hour: clase.fin.hours(), minute: clase.fin.minutes() };
    this.editForm.patchValue({
      id: clase.id,
      nombre: clase.nombre,
      descripcion: clase.descripcion,
      lugar: clase.lugar,
      inicio: clase.inicio,
      horaInicio: new FormControl(),
      fin: clase.fin,
      horaFin: new FormControl(),
      incidencias: clase.incidencias,
      monitorId: clase.monitorId,
      incidencia: clase.incidencia
    });
    if (clase.incidencia) {
      this.incidenciaForm.patchValue({
        nombre: clase.incidencia.nombre,
        descripcion: clase.incidencia.descripcion
      });
    }
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
      inicio: this.editForm
        .get(['inicio'])
        .value.hours(this.horaInicio.hour)
        .minutes(this.horaInicio.minute),
      fin: this.editForm
        .get(['fin'])
        .value.hours(this.horaFin.hour)
        .minutes(this.horaFin.minute),
      incidencias: this.editForm.get(['incidencias']).value,
      monitorId: this.editForm.get(['monitorId']).value,
      incidencia: this.createIncidenciaFromForm()
    };

    return entity;
  }

  private createIncidenciaFromForm(): IIncidencia {
    let incidencia = {
      ...new Incidencia(),
      nombre: this.incidenciaForm.get(['nombre']).value,
      descripcion: this.incidenciaForm.get(['descripcion']).value
    };
    if (this.clase.incidencia) {
      incidencia.id = this.clase.incidencia.id;
      incidencia.claseId = this.clase.incidencia.claseId;
    }
    return incidencia;
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

  onCheck(ob: any) {
    this.clase.incidencias = ob.target.checked;
    if (this.clase.incidencias && !this.clase.incidencia) this.clase.incidencia = new Incidencia();
  }
}
