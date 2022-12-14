import { Moment } from 'moment';
import { IIncidencia } from './incidencia.model';

export interface IClase {
  id?: number;
  nombre?: string;
  descripcion?: string;
  lugar?: string;
  inicio?: Moment;
  fin?: Moment;
  incidencias?: boolean;
  monitorId?: number;
  incidencia?: IIncidencia;
}

export class Clase implements IClase {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public lugar?: string,
    public inicio?: Moment,
    public fin?: Moment,
    public incidencias?: boolean,
    public monitorId?: number,
    public incidencia?: IIncidencia
  ) {
    this.incidencias = this.incidencias || false;
  }
}
