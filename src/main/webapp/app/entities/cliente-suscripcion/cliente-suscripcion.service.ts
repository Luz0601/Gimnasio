import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';

type EntityResponseType = HttpResponse<IClienteSuscripcion>;
type EntityArrayResponseType = HttpResponse<IClienteSuscripcion[]>;

@Injectable({ providedIn: 'root' })
export class ClienteSuscripcionService {
  public resourceUrl = SERVER_API_URL + 'api/cliente-suscripcions';

  constructor(protected http: HttpClient) {}

  create(clienteSuscripcion: IClienteSuscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteSuscripcion);
    return this.http
      .post<IClienteSuscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(clienteSuscripcion: IClienteSuscripcion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(clienteSuscripcion);
    return this.http
      .put<IClienteSuscripcion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClienteSuscripcion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClienteSuscripcion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(clienteSuscripcion: IClienteSuscripcion): IClienteSuscripcion {
    const copy: IClienteSuscripcion = Object.assign({}, clienteSuscripcion, {
      ultimoPago:
        clienteSuscripcion.ultimoPago != null && clienteSuscripcion.ultimoPago.isValid()
          ? clienteSuscripcion.ultimoPago.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ultimoPago = res.body.ultimoPago != null ? moment(res.body.ultimoPago) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((clienteSuscripcion: IClienteSuscripcion) => {
        clienteSuscripcion.ultimoPago = clienteSuscripcion.ultimoPago != null ? moment(clienteSuscripcion.ultimoPago) : null;
      });
    }
    return res;
  }
}
