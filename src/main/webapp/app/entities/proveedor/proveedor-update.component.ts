import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
  proveedor: IProveedor;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    telefono: [],
    email: []
  });

  constructor(protected proveedorService: ProveedorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.updateForm(proveedor);
      this.proveedor = proveedor;
    });
  }

  updateForm(proveedor: IProveedor) {
    this.editForm.patchValue({
      id: proveedor.id,
      nombre: proveedor.nombre,
      telefono: proveedor.telefono,
      email: proveedor.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proveedor = this.createFromForm();
    if (proveedor.id !== undefined) {
      this.subscribeToSaveResponse(this.proveedorService.update(proveedor));
    } else {
      this.subscribeToSaveResponse(this.proveedorService.create(proveedor));
    }
  }

  private createFromForm(): IProveedor {
    const entity = {
      ...new Proveedor(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      telefono: this.editForm.get(['telefono']).value,
      email: this.editForm.get(['email']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProveedor>>) {
    result.subscribe((res: HttpResponse<IProveedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
