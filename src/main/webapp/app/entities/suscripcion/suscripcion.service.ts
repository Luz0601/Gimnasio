import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISuscripcion } from 'app/shared/model/suscripcion.model';

type EntityResponseType = HttpResponse<ISuscripcion>;
type EntityArrayResponseType = HttpResponse<ISuscripcion[]>;

@Injectable({ providedIn: 'root' })
export class SuscripcionService {
  public resourceUrl = SERVER_API_URL + 'api/suscripcions';

  constructor(protected http: HttpClient) {}

  create(suscripcion: ISuscripcion): Observable<EntityResponseType> {
    return this.http.post<ISuscripcion>(this.resourceUrl, suscripcion, { observe: 'response' });
  }

  update(suscripcion: ISuscripcion): Observable<EntityResponseType> {
    return this.http.put<ISuscripcion>(this.resourceUrl, suscripcion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISuscripcion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISuscripcion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
