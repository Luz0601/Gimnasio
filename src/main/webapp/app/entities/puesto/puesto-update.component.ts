import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPuesto, Puesto } from 'app/shared/model/puesto.model';
import { PuestoService } from './puesto.service';

@Component({
  selector: 'jhi-puesto-update',
  templateUrl: './puesto-update.component.html'
})
export class PuestoUpdateComponent implements OnInit {
  puesto: IPuesto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    horario: [],
    salario: [null, [Validators.required]]
  });

  constructor(protected puestoService: PuestoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ puesto }) => {
      this.updateForm(this.puesto);
      this.puesto = puesto;
    });
  }

  updateForm(puesto: IPuesto) {
    this.editForm.patchValue({
      id: puesto.id,
      nombre: puesto.nombre,
      horario: puesto.horario,
      salario: puesto.salario
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const puesto = this.createFromForm();
    if (puesto.id !== null) {
      this.subscribeToSaveResponse(this.puestoService.update(puesto));
    } else {
      this.subscribeToSaveResponse(this.puestoService.create(puesto));
    }
  }

  private createFromForm(): IPuesto {
    const entity = {
      ...new Puesto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      horario: this.editForm.get(['horario']).value,
      salario: this.editForm.get(['salario']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuesto>>) {
    result.subscribe((res: HttpResponse<IPuesto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
