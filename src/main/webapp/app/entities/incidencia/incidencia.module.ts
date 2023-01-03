import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  IncidenciaComponent,
  // IncidenciaDetailComponent,
  IncidenciaUpdateComponent,
  IncidenciaDeletePopupComponent,
  IncidenciaDeleteDialogComponent,
  incidenciaRoute,
  incidenciaPopupRoute
} from './';
import { ClaseDetailComponent } from '../clase';

const ENTITY_STATES = [...incidenciaRoute, ...incidenciaPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IncidenciaComponent,
    IncidenciaUpdateComponent,
    IncidenciaDeleteDialogComponent,
    IncidenciaDeletePopupComponent,
    ClaseDetailComponent
  ],
  entryComponents: [
    IncidenciaComponent,
    IncidenciaUpdateComponent,
    IncidenciaDeleteDialogComponent,
    IncidenciaDeletePopupComponent,
    ClaseDetailComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioIncidenciaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
