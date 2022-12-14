import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INomina, Nomina } from 'app/shared/model/nomina.model';
import { NominaService } from './nomina.service';

@Component({
  selector: 'jhi-nomina-update',
  templateUrl: './nomina-update.component.html'
})
export class NominaUpdateComponent implements OnInit {
  nomina: INomina;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    iban: [],
    tipoContrato: []
  });

  constructor(protected nominaService: NominaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nomina }) => {
      this.updateForm(nomina);
      this.nomina = nomina;
    });
  }

  updateForm(nomina: INomina) {
    this.editForm.patchValue({
      id: nomina.id,
      iban: nomina.iban,
      tipoContrato: nomina.tipoContrato
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nomina = this.createFromForm();
    if (nomina.id !== undefined) {
      this.subscribeToSaveResponse(this.nominaService.update(nomina));
    } else {
      this.subscribeToSaveResponse(this.nominaService.create(nomina));
    }
  }

  private createFromForm(): INomina {
    const entity = {
      ...new Nomina(),
      id: this.editForm.get(['id']).value,
      iban: this.editForm.get(['iban']).value,
      tipoContrato: this.editForm.get(['tipoContrato']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INomina>>) {
    result.subscribe((res: HttpResponse<INomina>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
