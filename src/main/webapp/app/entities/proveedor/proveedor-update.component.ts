import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IProveedor, Proveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from './proveedor.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-proveedor-update',
  templateUrl: './proveedor-update.component.html'
})
export class ProveedorUpdateComponent implements OnInit {
  proveedor: IProveedor;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    email: [null, [Validators.required]]
  });

  constructor(
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected modal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proveedor }) => {
      this.updateForm(this.proveedor);
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
    if (proveedor.id !== null) {
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
    window.location.reload();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
