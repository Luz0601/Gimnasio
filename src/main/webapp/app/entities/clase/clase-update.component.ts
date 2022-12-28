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
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { noUndefined } from '@angular/compiler/src/util';

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
    fecha: [null, [Validators.required]],
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
    private fb: FormBuilder,
    protected modal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    if (!this.clase) {
      this.clase = new Clase();
    }
    this.updateForm(this.clase);

    this.empleadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpleado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpleado[]>) => response.body)
      )
      .subscribe((res: IEmpleado[]) => (this.monitors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(clase: IClase) {
    if (clase.inicio) this.horaInicio = { hour: clase.inicio.hours(), minute: clase.inicio.minutes() };
    if (clase.fin) this.horaFin = { hour: clase.fin.hours(), minute: clase.fin.minutes() };
    this.editForm.patchValue({
      id: clase.id,
      nombre: clase.nombre,
      descripcion: clase.descripcion,
      lugar: clase.lugar,
      fecha: clase.inicio,
      horaInicio: new FormControl(),
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
    const fechaI = moment(this.editForm.get(['fecha']).value.toISOString());
    const fechaF = moment(this.editForm.get(['fecha']).value.toISOString());

    const entity = {
      ...new Clase(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      lugar: this.editForm.get(['lugar']).value,
      inicio: fechaI.hours(this.horaInicio.hour).minutes(this.horaInicio.minute),
      fin: fechaF.hours(this.horaFin.hour).minutes(this.horaFin.minute),
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
    window.location.reload();
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
