import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GimnasioSharedModule } from 'app/shared';
import { JhiLanguageHelper } from 'app/core';
import { JhiLanguageService } from 'ng-jhipster';
import { HorarioClaseComponent, horarioClaseRoute } from './';

const ENTITY_STATES = [...horarioClaseRoute];

@NgModule({
  imports: [GimnasioSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [HorarioClaseComponent],
  entryComponents: [HorarioClaseComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioHorarioClaseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        languageService.changeLanguage(languageKey);
      }
    });
  }
}
