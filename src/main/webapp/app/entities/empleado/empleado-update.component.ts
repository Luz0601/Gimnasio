import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IEmpleado, Empleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from './empleado.service';
import { INomina } from 'app/shared/model/nomina.model';
import { NominaService } from 'app/entities/nomina';
import { IPuesto } from 'app/shared/model/puesto.model';
import { PuestoService } from 'app/entities/puesto';

@Component({
  selector: 'jhi-empleado-update',
  templateUrl: './empleado-update.component.html'
})
export class EmpleadoUpdateComponent implements OnInit {
  empleado: IEmpleado;
  isSaving: boolean;

  nominas: INomina[];

  puestos: IPuesto[];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    dni: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    apellido: [],
    telefono: [null, [Validators.required]],
    fechaNacimiento: [null, [Validators.required]],
    email: [null, []],
    direccion: [null, [Validators.required]],
    diasVacaciones: [],
    especialidad: [null, [Validators.required]],
    nominaId: [],
    puestoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected empleadoService: EmpleadoService,
    protected nominaService: NominaService,
    protected puestoService: PuestoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empleado }) => {
      this.updateForm(empleado);
      this.empleado = empleado;
    });
    this.nominaService
      .query({ filter: 'empleado-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<INomina[]>) => mayBeOk.ok),
        map((response: HttpResponse<INomina[]>) => response.body)
      )
      .subscribe(
        (res: INomina[]) => {
          if (!this.empleado.nominaId) {
            this.nominas = res;
          } else {
            this.nominaService
              .find(this.empleado.nominaId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<INomina>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<INomina>) => subResponse.body)
              )
              .subscribe(
                (subRes: INomina) => (this.nominas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.puestoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPuesto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPuesto[]>) => response.body)
      )
      .subscribe((res: IPuesto[]) => (this.puestos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(empleado: IEmpleado) {
    this.editForm.patchValue({
      id: empleado.id,
      dni: empleado.dni,
      nombre: empleado.nombre,
      apellido: empleado.apellido,
      telefono: empleado.telefono,
      fechaNacimiento: empleado.fechaNacimiento,
      email: empleado.email,
      direccion: empleado.direccion,
      diasVacaciones: empleado.diasVacaciones,
      especialidad: empleado.especialidad,
      nominaId: empleado.nominaId,
      puestoId: empleado.puestoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empleado = this.createFromForm();
    if (empleado.id !== undefined) {
      this.subscribeToSaveResponse(this.empleadoService.update(empleado));
    } else {
      this.subscribeToSaveResponse(this.empleadoService.create(empleado));
    }
  }

  private createFromForm(): IEmpleado {
    const entity = {
      ...new Empleado(),
      id: this.editForm.get(['id']).value,
      dni: this.editForm.get(['dni']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      telefono: this.editForm.get(['telefono']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      email: this.editForm.get(['email']).value,
      direccion: this.editForm.get(['direccion']).value,
      diasVacaciones: this.editForm.get(['diasVacaciones']).value,
      especialidad: this.editForm.get(['especialidad']).value,
      nominaId: this.editForm.get(['nominaId']).value,
      puestoId: this.editForm.get(['puestoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpleado>>) {
    result.subscribe((res: HttpResponse<IEmpleado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackNominaById(index: number, item: INomina) {
    return item.id;
  }

  trackPuestoById(index: number, item: IPuesto) {
    return item.id;
  }
}
