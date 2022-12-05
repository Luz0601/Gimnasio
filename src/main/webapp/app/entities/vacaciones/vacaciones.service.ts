import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVacaciones } from 'app/shared/model/vacaciones.model';

type EntityResponseType = HttpResponse<IVacaciones>;
type EntityArrayResponseType = HttpResponse<IVacaciones[]>;

@Injectable({ providedIn: 'root' })
export class VacacionesService {
  public resourceUrl = SERVER_API_URL + 'api/vacaciones';

  constructor(protected http: HttpClient) {}

  create(vacaciones: IVacaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vacaciones);
    return this.http
      .post<IVacaciones>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vacaciones: IVacaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vacaciones);
    return this.http
      .put<IVacaciones>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVacaciones>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVacaciones[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vacaciones: IVacaciones): IVacaciones {
    const copy: IVacaciones = Object.assign({}, vacaciones, {
      inicio: vacaciones.inicio != null && vacaciones.inicio.isValid() ? vacaciones.inicio.format(DATE_FORMAT) : null,
      fin: vacaciones.fin != null && vacaciones.fin.isValid() ? vacaciones.fin.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inicio = res.body.inicio != null ? moment(res.body.inicio) : null;
      res.body.fin = res.body.fin != null ? moment(res.body.fin) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vacaciones: IVacaciones) => {
        vacaciones.inicio = vacaciones.inicio != null ? moment(vacaciones.inicio) : null;
        vacaciones.fin = vacaciones.fin != null ? moment(vacaciones.fin) : null;
      });
    }
    return res;
  }
}
