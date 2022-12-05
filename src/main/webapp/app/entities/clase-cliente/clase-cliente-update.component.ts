import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IClaseCliente, ClaseCliente } from 'app/shared/model/clase-cliente.model';
import { ClaseClienteService } from './clase-cliente.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IClase } from 'app/shared/model/clase.model';
import { ClaseService } from 'app/entities/clase';

@Component({
  selector: 'jhi-clase-cliente-update',
  templateUrl: './clase-cliente-update.component.html'
})
export class ClaseClienteUpdateComponent implements OnInit {
  claseCliente: IClaseCliente;
  isSaving: boolean;

  clientes: ICliente[];

  clases: IClase[];

  editForm = this.fb.group({
    id: [],
    clienteId: [],
    claseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected claseClienteService: ClaseClienteService,
    protected clienteService: ClienteService,
    protected claseService: ClaseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ claseCliente }) => {
      this.updateForm(claseCliente);
      this.claseCliente = claseCliente;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.claseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClase[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClase[]>) => response.body)
      )
      .subscribe((res: IClase[]) => (this.clases = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(claseCliente: IClaseCliente) {
    this.editForm.patchValue({
      id: claseCliente.id,
      clienteId: claseCliente.clienteId,
      claseId: claseCliente.claseId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const claseCliente = this.createFromForm();
    if (claseCliente.id !== undefined) {
      this.subscribeToSaveResponse(this.claseClienteService.update(claseCliente));
    } else {
      this.subscribeToSaveResponse(this.claseClienteService.create(claseCliente));
    }
  }

  private createFromForm(): IClaseCliente {
    const entity = {
      ...new ClaseCliente(),
      id: this.editForm.get(['id']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      claseId: this.editForm.get(['claseId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClaseCliente>>) {
    result.subscribe((res: HttpResponse<IClaseCliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackClaseById(index: number, item: IClase) {
    return item.id;
  }
}
