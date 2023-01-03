import { Moment } from 'moment';

export interface IEmpleado {
  id?: number;
  dni?: string;
  nombre?: string;
  apellido?: string;
  telefono?: number;
  fechaNacimiento?: Moment;
  email?: string;
  direccion?: string;
  diasVacaciones?: number;
  especialidad?: string;
  nominaId?: number;
  nominaTipoContrato?: string;
  puestoId?: number;
  puestoNombre?: string;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public dni?: string,
    public nombre?: string,
    public apellido?: string,
    public telefono?: number,
    public fechaNacimiento?: Moment,
    public email?: string,
    public direccion?: string,
    public diasVacaciones?: number,
    public especialidad?: string,
    public nominaId?: number,
    public nominaTipoContrato?: string,
    public puestoId?: number,
    public puestoNombre?: string
  ) {}
}
