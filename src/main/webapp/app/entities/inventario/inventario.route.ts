import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Inventario } from 'app/shared/model/inventario.model';
import { InventarioService } from './inventario.service';
import { InventarioComponent } from './inventario.component';
import { InventarioDetailComponent } from './inventario-detail.component';
import { InventarioUpdateComponent } from './inventario-update.component';
import { InventarioDeletePopupComponent } from './inventario-delete-dialog.component';
import { IInventario } from 'app/shared/model/inventario.model';

@Injectable({ providedIn: 'root' })
export class InventarioResolve implements Resolve<IInventario> {
  constructor(private service: InventarioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInventario> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Inventario>) => response.ok),
        map((inventario: HttpResponse<Inventario>) => inventario.body)
      );
    }
    return of(new Inventario());
  }
}

export const inventarioRoute: Routes = [
  {
    path: '',
    component: InventarioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      defaultSort: 'id,asc',
      pageTitle: 'gimnasioApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InventarioDetailComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InventarioUpdateComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InventarioUpdateComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inventarioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InventarioDeletePopupComponent,
    resolve: {
      inventario: InventarioResolve
    },
    data: {
      authorities: ['ROLE_USER', 'ROLE_MONITOR'],
      pageTitle: 'gimnasioApp.inventario.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
