/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { InventarioService } from 'app/entities/inventario/inventario.service';
import { IInventario, Inventario } from 'app/shared/model/inventario.model';

describe('Service Tests', () => {
  describe('Inventario Service', () => {
    let injector: TestBed;
    let service: InventarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IInventario;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InventarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Inventario(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, false, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            ultRevision: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Inventario', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ultRevision: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            ultRevision: currentDate
          },
          returnedFromService
        );
        service
          .create(new Inventario(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Inventario', async () => {
        const returnedFromService = Object.assign(
          {
            ref: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            cantidad: 1,
            estado: true,
            ultRevision: currentDate.format(DATE_FORMAT),
            periodoRevision: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ultRevision: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Inventario', async () => {
        const returnedFromService = Object.assign(
          {
            ref: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            cantidad: 1,
            estado: true,
            ultRevision: currentDate.format(DATE_FORMAT),
            periodoRevision: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            ultRevision: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Inventario', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
