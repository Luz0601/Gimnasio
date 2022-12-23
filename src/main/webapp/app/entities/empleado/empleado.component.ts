import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { IEmpleado } from 'app/shared/model/empleado.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { EmpleadoService } from './empleado.service';
import { EmpleadoDetailComponent } from './empleado-detail.component';
import { EmpleadoUpdateComponent } from './empleado-update.component';
import { NominaDetailComponent, NominaService } from '../nomina';
import { INomina } from 'app/shared/model/nomina.model';

@Component({
  selector: 'jhi-empleado',
  templateUrl: './empleado.component.html'
})
export class EmpleadoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  empleados: IEmpleado[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected empleadoService: EmpleadoService,
    protected nominaService: NominaService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.empleadoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEmpleado[]>) => this.paginateEmpleados(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }
  open(content) {
    const modalRef = this.modalService.open(EmpleadoDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.empleado = content;
  }
  editar(content) {
    const modalRef = this.modalService.open(EmpleadoUpdateComponent, { ariaLabelledBy: 'modal-basic-title' });
    modalRef.componentInstance.empleado = content;
  }

  nomina(content) {
    this.nominaService.find(content).subscribe(
      (res: HttpResponse<INomina>) => {
        const modalRef = this.modalService.open(NominaDetailComponent, { ariaLabelledBy: 'modal-basic-title' });
        modalRef.componentInstance.nomina = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  transition() {
    this.router.navigate(['/empleado'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/empleado',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEmpleados();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEmpleado) {
    return item.id;
  }

  registerChangeInEmpleados() {
    this.eventSubscriber = this.eventManager.subscribe('empleadoListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEmpleados(data: IEmpleado[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.empleados = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
