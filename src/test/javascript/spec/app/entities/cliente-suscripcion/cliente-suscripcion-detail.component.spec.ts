/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GimnasioTestModule } from '../../../test.module';
import { ClienteSuscripcionDetailComponent } from 'app/entities/cliente-suscripcion/cliente-suscripcion-detail.component';
import { ClienteSuscripcion } from 'app/shared/model/cliente-suscripcion.model';

describe('Component Tests', () => {
  describe('ClienteSuscripcion Management Detail Component', () => {
    let comp: ClienteSuscripcionDetailComponent;
    let fixture: ComponentFixture<ClienteSuscripcionDetailComponent>;
    const route = ({ data: of({ clienteSuscripcion: new ClienteSuscripcion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GimnasioTestModule],
        declarations: [ClienteSuscripcionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ClienteSuscripcionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClienteSuscripcionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.clienteSuscripcion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
