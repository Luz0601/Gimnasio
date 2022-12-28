import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { EmpleadoDetailComponent } from 'app/entities/empleado';
import { IncidenciaDetailComponent } from 'app/entities/incidencia';
import { NominaDetailComponent } from 'app/entities/nomina';
import { ProveedorDetailComponent } from 'app/entities/proveedor';
import { GimnasioSharedLibsModule, GimnasioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [GimnasioSharedLibsModule, GimnasioSharedCommonModule],
  declarations: [
    JhiLoginModalComponent,
    HasAnyAuthorityDirective,
    IncidenciaDetailComponent,
    EmpleadoDetailComponent,
    NominaDetailComponent,
    ProveedorDetailComponent
  ],
  entryComponents: [
    JhiLoginModalComponent,
    IncidenciaDetailComponent,
    EmpleadoDetailComponent,
    NominaDetailComponent,
    ProveedorDetailComponent
  ],
  exports: [GimnasioSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioSharedModule {
  static forRoot() {
    return {
      ngModule: GimnasioSharedModule
    };
  }
}
