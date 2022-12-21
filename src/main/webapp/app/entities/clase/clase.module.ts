import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import { IncidenciaDetailComponent } from 'app/entities/incidencia/incidencia-detail.component';
import { EmpleadoDetailComponent } from '../empleado/empleado-detail.component';
import {
  ClaseComponent,
  ClaseDetailComponent,
  ClaseUpdateComponent,
  ClaseDeletePopupComponent,
  ClaseDeleteDialogComponent,
  claseRoute,
  clasePopupRoute
} from './';

const ENTITY_STATES = [...claseRoute, ...clasePopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ClaseComponent, ClaseDetailComponent, ClaseUpdateComponent, ClaseDeleteDialogComponent, ClaseDeletePopupComponent],
  entryComponents: [ClaseComponent, ClaseUpdateComponent, ClaseDeleteDialogComponent, ClaseDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioClaseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
