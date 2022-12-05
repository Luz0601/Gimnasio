import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClaseCliente } from 'app/shared/model/clase-cliente.model';

type EntityResponseType = HttpResponse<IClaseCliente>;
type EntityArrayResponseType = HttpResponse<IClaseCliente[]>;

@Injectable({ providedIn: 'root' })
export class ClaseClienteService {
  public resourceUrl = SERVER_API_URL + 'api/clase-clientes';

  constructor(protected http: HttpClient) {}

  create(claseCliente: IClaseCliente): Observable<EntityResponseType> {
    return this.http.post<IClaseCliente>(this.resourceUrl, claseCliente, { observe: 'response' });
  }

  update(claseCliente: IClaseCliente): Observable<EntityResponseType> {
    return this.http.put<IClaseCliente>(this.resourceUrl, claseCliente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IClaseCliente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IClaseCliente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
