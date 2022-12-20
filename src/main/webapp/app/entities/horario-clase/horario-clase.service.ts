import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHorarioClase } from 'app/shared/model/horario-clase.model';
import { Observable } from 'rxjs';

type EntityResponseType = HttpResponse<IHorarioClase>;
type EntityArrayResponseType = HttpResponse<IHorarioClase[]>;

@Injectable({ providedIn: 'root' })
export class HorarioClaseService {
  public resourceUrl = SERVER_API_URL + 'api/horario-clase';

  constructor(protected http: HttpClient) {}

  find(claseId: number): Observable<EntityResponseType> {
    return this.http.get<IHorarioClase>(`${this.resourceUrl}/${claseId}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHorarioClase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
