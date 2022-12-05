import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventario } from 'app/shared/model/inventario.model';

type EntityResponseType = HttpResponse<IInventario>;
type EntityArrayResponseType = HttpResponse<IInventario[]>;

@Injectable({ providedIn: 'root' })
export class InventarioService {
  public resourceUrl = SERVER_API_URL + 'api/inventarios';

  constructor(protected http: HttpClient) {}

  create(inventario: IInventario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventario);
    return this.http
      .post<IInventario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventario: IInventario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventario);
    return this.http
      .put<IInventario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventario: IInventario): IInventario {
    const copy: IInventario = Object.assign({}, inventario, {
      ultRevision: inventario.ultRevision != null && inventario.ultRevision.isValid() ? inventario.ultRevision.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ultRevision = res.body.ultRevision != null ? moment(res.body.ultRevision) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventario: IInventario) => {
        inventario.ultRevision = inventario.ultRevision != null ? moment(inventario.ultRevision) : null;
      });
    }
    return res;
  }
}
