import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClase } from 'app/shared/model/clase.model';

type EntityResponseType = HttpResponse<IClase>;
type EntityArrayResponseType = HttpResponse<IClase[]>;

@Injectable({ providedIn: 'root' })
export class ClaseService {
  public resourceUrl = SERVER_API_URL + 'api/clases';

  constructor(protected http: HttpClient) {}

  create(clase: IClase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clase);
    return this.http
      .post<IClase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(clase: IClase): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clase);
    return this.http
      .put<IClase>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClase>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClase[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(clase: IClase): IClase {
    const copy: IClase = Object.assign({}, clase, {
      inicio: clase.inicio != null && clase.inicio.isValid() ? clase.inicio.valueOf() : null,
      fin: clase.fin != null && clase.fin.isValid() ? clase.fin.valueOf() : null
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
      res.body.forEach((clase: IClase) => {
        clase.inicio = clase.inicio != null ? moment(clase.inicio) : null;
        clase.fin = clase.fin != null ? moment(clase.fin) : null;
      });
    }
    return res;
  }
}
