import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IClienteSuscripcion, ClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';
import { ClienteSuscripcionService } from './cliente-suscripcion.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';
import { SuscripcionService } from 'app/entities/suscripcion';

@Component({
  selector: 'jhi-cliente-suscripcion-update',
  templateUrl: './cliente-suscripcion-update.component.html'
})
export class ClienteSuscripcionUpdateComponent implements OnInit {
  clienteSuscripcion: IClienteSuscripcion;
  isSaving: boolean;

  clientes: ICliente[];

  suscripcions: ISuscripcion[];
  ultimoPagoDp: any;

  editForm = this.fb.group({
    id: [],
    ultimoPago: [null, [Validators.required]],
    metodoPago: [],
    clienteId: [],
    suscripcionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clienteSuscripcionService: ClienteSuscripcionService,
    protected clienteService: ClienteService,
    protected suscripcionService: SuscripcionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clienteSuscripcion }) => {
      this.updateForm(clienteSuscripcion);
      this.clienteSuscripcion = clienteSuscripcion;
    });
    this.clienteService
      .query({ filter: 'clientesuscripcion-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe(
        (res: ICliente[]) => {
          if (!this.clienteSuscripcion.clienteId) {
            this.clientes = res;
          } else {
            this.clienteService
              .find(this.clienteSuscripcion.clienteId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICliente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICliente>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICliente) => (this.clientes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.suscripcionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuscripcion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuscripcion[]>) => response.body)
      )
      .subscribe((res: ISuscripcion[]) => (this.suscripcions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(clienteSuscripcion: IClienteSuscripcion) {
    this.editForm.patchValue({
      id: clienteSuscripcion.id,
      ultimoPago: clienteSuscripcion.ultimoPago,
      metodoPago: clienteSuscripcion.metodoPago,
      clienteId: clienteSuscripcion.clienteId,
      suscripcionId: clienteSuscripcion.suscripcionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clienteSuscripcion = this.createFromForm();
    if (clienteSuscripcion.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteSuscripcionService.update(clienteSuscripcion));
    } else {
      this.subscribeToSaveResponse(this.clienteSuscripcionService.create(clienteSuscripcion));
    }
  }

  private createFromForm(): IClienteSuscripcion {
    const entity = {
      ...new ClienteSuscripcion(),
      id: this.editForm.get(['id']).value,
      ultimoPago: this.editForm.get(['ultimoPago']).value,
      metodoPago: this.editForm.get(['metodoPago']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      suscripcionId: this.editForm.get(['suscripcionId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClienteSuscripcion>>) {
    result.subscribe((res: HttpResponse<IClienteSuscripcion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSuscripcionById(index: number, item: ISuscripcion) {
    return item.id;
  }
}
