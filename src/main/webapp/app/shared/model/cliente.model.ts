import { Moment } from 'moment';

export interface ICliente {
  id?: number;
  dni?: string;
  nombre?: string;
  apellido?: string;
  telefono?: number;
  fechaNacimiento?: Moment;
  email?: string;
  direccion?: string;
  ciclo?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public dni?: string,
    public nombre?: string,
    public apellido?: string,
    public telefono?: number,
    public fechaNacimiento?: Moment,
    public email?: string,
    public direccion?: string,
    public ciclo?: number
  ) {}
}
