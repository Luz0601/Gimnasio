import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  cliente: ICliente;
  isSaving: boolean;
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    dni: [null, [Validators.required]],
    nombre: [],
    apellido: [],
    telefono: [],
    fechaNacimiento: [],
    email: [null, []],
    direccion: [],
    ciclo: []
  });

  constructor(protected clienteService: ClienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
      this.cliente = cliente;
    });
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      dni: cliente.dni,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      telefono: cliente.telefono,
      fechaNacimiento: cliente.fechaNacimiento,
      email: cliente.email,
      direccion: cliente.direccion,
      ciclo: cliente.ciclo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    const entity = {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      dni: this.editForm.get(['dni']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      telefono: this.editForm.get(['telefono']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      email: this.editForm.get(['email']).value,
      direccion: this.editForm.get(['direccion']).value,
      ciclo: this.editForm.get(['ciclo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
