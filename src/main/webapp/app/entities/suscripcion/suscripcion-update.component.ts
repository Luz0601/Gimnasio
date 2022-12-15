import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISuscripcion, Suscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from './suscripcion.service';

@Component({
  selector: 'jhi-suscripcion-update',
  templateUrl: './suscripcion-update.component.html'
})
export class SuscripcionUpdateComponent implements OnInit {
  suscripcion: ISuscripcion;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    precio: [null, [Validators.required]],
    periodo: [null, [Validators.required]]
  });

  constructor(protected suscripcionService: SuscripcionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ suscripcion }) => {
      this.updateForm(suscripcion);
      this.suscripcion = suscripcion;
    });
  }

  updateForm(suscripcion: ISuscripcion) {
    this.editForm.patchValue({
      id: suscripcion.id,
      precio: suscripcion.precio,
      periodo: suscripcion.periodo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const suscripcion = this.createFromForm();
    if (suscripcion.id !== undefined) {
      this.subscribeToSaveResponse(this.suscripcionService.update(suscripcion));
    } else {
      this.subscribeToSaveResponse(this.suscripcionService.create(suscripcion));
    }
  }

  private createFromForm(): ISuscripcion {
    const entity = {
      ...new Suscripcion(),
      id: this.editForm.get(['id']).value,
      precio: this.editForm.get(['precio']).value,
      periodo: this.editForm.get(['periodo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuscripcion>>) {
    result.subscribe((res: HttpResponse<ISuscripcion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
