import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPuesto } from 'app/shared/model/puesto.model';

type EntityResponseType = HttpResponse<IPuesto>;
type EntityArrayResponseType = HttpResponse<IPuesto[]>;

@Injectable({ providedIn: 'root' })
export class PuestoService {
  public resourceUrl = SERVER_API_URL + 'api/puestos';

  constructor(protected http: HttpClient) {}

  create(puesto: IPuesto): Observable<EntityResponseType> {
    return this.http.post<IPuesto>(this.resourceUrl, puesto, { observe: 'response' });
  }

  update(puesto: IPuesto): Observable<EntityResponseType> {
    return this.http.put<IPuesto>(this.resourceUrl, puesto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPuesto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPuesto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
