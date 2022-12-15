import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVacaciones, Vacaciones } from 'app/shared/model/vacaciones.model';
import { VacacionesService } from './vacaciones.service';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { EmpleadoService } from 'app/entities/empleado';

@Component({
  selector: 'jhi-vacaciones-update',
  templateUrl: './vacaciones-update.component.html'
})
export class VacacionesUpdateComponent implements OnInit {
  vacaciones: IVacaciones;
  isSaving: boolean;

  empleados: IEmpleado[];
  inicioDp: any;
  finDp: any;

  editForm = this.fb.group({
    id: [],
    inicio: [null, [Validators.required]],
    fin: [null, [Validators.required]],
    empleadoId: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vacacionesService: VacacionesService,
    protected empleadoService: EmpleadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vacaciones }) => {
      this.updateForm(vacaciones);
      this.vacaciones = vacaciones;
    });
    this.empleadoService
      .query({ filter: 'vacaciones-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpleado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpleado[]>) => response.body)
      )
      .subscribe(
        (res: IEmpleado[]) => {
          if (!this.vacaciones.empleadoId) {
            this.empleados = res;
          } else {
            this.empleadoService
              .find(this.vacaciones.empleadoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEmpleado>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEmpleado>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEmpleado) => (this.empleados = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(vacaciones: IVacaciones) {
    this.editForm.patchValue({
      id: vacaciones.id,
      inicio: vacaciones.inicio,
      fin: vacaciones.fin,
      empleadoId: vacaciones.empleadoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vacaciones = this.createFromForm();
    if (vacaciones.id !== undefined) {
      this.subscribeToSaveResponse(this.vacacionesService.update(vacaciones));
    } else {
      this.subscribeToSaveResponse(this.vacacionesService.create(vacaciones));
    }
  }

  private createFromForm(): IVacaciones {
    const entity = {
      ...new Vacaciones(),
      id: this.editForm.get(['id']).value,
      inicio: this.editForm.get(['inicio']).value,
      fin: this.editForm.get(['fin']).value,
      empleadoId: this.editForm.get(['empleadoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVacaciones>>) {
    result.subscribe((res: HttpResponse<IVacaciones>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
