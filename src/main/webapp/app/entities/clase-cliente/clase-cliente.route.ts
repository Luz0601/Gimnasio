import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClaseCliente } from 'app/shared/model/clase-cliente.model';
import { ClaseClienteService } from './clase-cliente.service';
import { ClaseClienteComponent } from './clase-cliente.component';
import { ClaseClienteDetailComponent } from './clase-cliente-detail.component';
import { ClaseClienteUpdateComponent } from './clase-cliente-update.component';
import { ClaseClienteDeletePopupComponent } from './clase-cliente-delete-dialog.component';
import { IClaseCliente } from 'app/shared/model/clase-cliente.model';

@Injectable({ providedIn: 'root' })
export class ClaseClienteResolve implements Resolve<IClaseCliente> {
  constructor(private service: ClaseClienteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IClaseCliente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ClaseCliente>) => response.ok),
        map((claseCliente: HttpResponse<ClaseCliente>) => claseCliente.body)
      );
    }
    return of(new ClaseCliente());
  }
}

export const claseClienteRoute: Routes = [
  {
    path: '',
    component: ClaseClienteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.claseCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ClaseClienteDetailComponent,
    resolve: {
      claseCliente: ClaseClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.claseCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ClaseClienteUpdateComponent,
    resolve: {
      claseCliente: ClaseClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.claseCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ClaseClienteUpdateComponent,
    resolve: {
      claseCliente: ClaseClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.claseCliente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const claseClientePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ClaseClienteDeletePopupComponent,
    resolve: {
      claseCliente: ClaseClienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gimnasioApp.claseCliente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
