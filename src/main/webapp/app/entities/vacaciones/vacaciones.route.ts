import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vacaciones } from 'app/shared/model/vacaciones.model';
import { VacacionesService } from './vacaciones.service';
import { VacacionesComponent } from './vacaciones.component';
import { VacacionesDetailComponent } from './vacaciones-detail.component';
import { VacacionesUpdateComponent } from './vacaciones-update.component';
import { VacacionesDeletePopupComponent } from './vacaciones-delete-dialog.component';
import { IVacaciones } from 'app/shared/model/vacaciones.model';

@Injectable({ providedIn: 'root' })
export class VacacionesResolve implements Resolve<IVacaciones> {
  constructor(private service: VacacionesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVacaciones> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vacaciones>) => response.ok),
        map((vacaciones: HttpResponse<Vacaciones>) => vacaciones.body)
      );
    }
    return of(new Vacaciones());
  }
}

export const vacacionesRoute: Routes = [
  {
    path: '',
    component: VacacionesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.vacaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VacacionesDetailComponent,
    resolve: {
      vacaciones: VacacionesResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.vacaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VacacionesUpdateComponent,
    resolve: {
      vacaciones: VacacionesResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.vacaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VacacionesUpdateComponent,
    resolve: {
      vacaciones: VacacionesResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.vacaciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vacacionesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VacacionesDeletePopupComponent,
    resolve: {
      vacaciones: VacacionesResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.vacaciones.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
