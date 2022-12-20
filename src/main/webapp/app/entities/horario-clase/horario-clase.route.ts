import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { HorarioClase, IHorarioClase } from 'app/shared/model/horario-clase.model';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HorarioClaseComponent } from './horario-clase.component';
import { HorarioClaseService } from './horario-clase.service';
import { UserRouteAccessService } from 'app/core';

@Injectable({ providedIn: 'root' })
export class HorarioClaseResolve implements Resolve<IHorarioClase> {
  constructor(private service: HorarioClaseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IHorarioClase> {
    const claseId = route.params['claseId'] ? route.params['claseId'] : null;
    if (claseId) {
      return this.service.find(claseId).pipe(
        filter((response: HttpResponse<HorarioClase>) => response.ok),
        map((horarioClase: HttpResponse<HorarioClase>) => horarioClase.body)
      );
    }
    return of(new HorarioClase());
  }
}

export const horarioClaseRoute: Routes = [
  {
    path: '',
    component: HorarioClaseComponent,
    resolve: {
      // pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'inicio,asc',
      pageTitle: 'gimnasioApp.horarioClase.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
