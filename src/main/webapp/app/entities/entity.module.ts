import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'clase',
        loadChildren: './clase/clase.module#GimnasioClaseModule'
      },
      {
        path: 'suscripcion',
        loadChildren: './suscripcion/suscripcion.module#GimnasioSuscripcionModule'
      },
      {
        path: 'empleado',
        loadChildren: './empleado/empleado.module#GimnasioEmpleadoModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#GimnasioClienteModule'
      },
      {
        path: 'incidencia',
        loadChildren: './incidencia/incidencia.module#GimnasioIncidenciaModule'
      },
      {
        path: 'puesto',
        loadChildren: './puesto/puesto.module#GimnasioPuestoModule'
      },
      {
        path: 'nomina',
        loadChildren: './nomina/nomina.module#GimnasioNominaModule'
      },
      {
        path: 'vacaciones',
        loadChildren: './vacaciones/vacaciones.module#GimnasioVacacionesModule'
      },
      {
        path: 'cliente-suscripcion',
        loadChildren: './cliente-suscripcion/cliente-suscripcion.module#GimnasioClienteSuscripcionModule'
      },
      {
        path: 'clase-cliente',
        loadChildren: './clase-cliente/clase-cliente.module#GimnasioClaseClienteModule'
      },
      {
        path: 'inventario',
        loadChildren: './inventario/inventario.module#GimnasioInventarioModule'
      },
      {
        path: 'proveedor',
        loadChildren: './proveedor/proveedor.module#GimnasioProveedorModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GimnasioEntityModule {}
