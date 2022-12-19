import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IInventario, Inventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { IProveedor } from 'app/shared/model/proveedor.model';
import { ProveedorService } from 'app/entities/proveedor';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-inventario-update',
  templateUrl: './inventario-update.component.html'
})
export class InventarioUpdateComponent implements OnInit {
  inventario: IInventario;
  isSaving: boolean;

  proveedors: IProveedor[];
  ultRevisionDp: any;

  editForm = this.fb.group({
    id: [],
    ref: [null, [Validators.required]],
    nombre: [null, [Validators.required]],
    descripcion: [],
    cantidad: [null, [Validators.required]],
    estado: [],
    ultRevision: [null, [Validators.required]],
    periodoRevision: [null, [Validators.required]],
    proveedorId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected inventarioService: InventarioService,
    protected proveedorService: ProveedorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected modal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inventario }) => {
      this.updateForm(this.inventario);
      this.inventario = inventario;
    });
    this.proveedorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProveedor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProveedor[]>) => response.body)
      )
      .subscribe((res: IProveedor[]) => (this.proveedors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(inventario: IInventario) {
    this.editForm.patchValue({
      id: inventario.id,
      ref: inventario.ref,
      nombre: inventario.nombre,
      descripcion: inventario.descripcion,
      cantidad: inventario.cantidad,
      estado: inventario.estado,
      ultRevision: inventario.ultRevision,
      periodoRevision: inventario.periodoRevision,
      proveedorId: inventario.proveedorId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inventario = this.createFromForm();
    if (inventario.id !== null) {
      this.subscribeToSaveResponse(this.inventarioService.update(inventario));
    } else {
      this.subscribeToSaveResponse(this.inventarioService.create(inventario));
    }
  }

  private createFromForm(): IInventario {
    const entity = {
      ...new Inventario(),
      id: this.editForm.get(['id']).value,
      ref: this.editForm.get(['ref']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      estado: this.editForm.get(['estado']).value,
      ultRevision: this.editForm.get(['ultRevision']).value,
      periodoRevision: this.editForm.get(['periodoRevision']).value,
      proveedorId: this.editForm.get(['proveedorId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventario>>) {
    result.subscribe((res: HttpResponse<IInventario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    window.location.reload();
    this.isSaving = false;
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProveedorById(index: number, item: IProveedor) {
    return item.id;
  }
}
