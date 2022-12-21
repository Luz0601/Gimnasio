import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { GimnasioSharedModule } from 'app/shared';
import {
  EmpleadoComponent,
  EmpleadoUpdateComponent,
  EmpleadoDeletePopupComponent,
  EmpleadoDeleteDialogComponent,
  empleadoRoute,
  empleadoPopupRoute
} from './';

const ENTITY_STATES = [...empleadoRoute, ...empleadoPopupRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [EmpleadoComponent, EmpleadoUpdateComponent, EmpleadoDeleteDialogComponent, EmpleadoDeletePopupComponent],
  entryComponents: [EmpleadoComponent, EmpleadoUpdateComponent, EmpleadoDeleteDialogComponent, EmpleadoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioEmpleadoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
