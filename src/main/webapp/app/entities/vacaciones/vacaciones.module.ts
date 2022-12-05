import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  VacacionesComponent,
  VacacionesDetailComponent,
  VacacionesUpdateComponent,
  VacacionesDeletePopupComponent,
  VacacionesDeleteDialogComponent,
  vacacionesRoute,
  vacacionesPopupRoute
} from './';

const ENTITY_STATES = [...vacacionesRoute, ...vacacionesPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VacacionesComponent,
    VacacionesDetailComponent,
    VacacionesUpdateComponent,
    VacacionesDeleteDialogComponent,
    VacacionesDeletePopupComponent
  ],
  entryComponents: [VacacionesComponent, VacacionesUpdateComponent, VacacionesDeleteDialogComponent, VacacionesDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioVacacionesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
