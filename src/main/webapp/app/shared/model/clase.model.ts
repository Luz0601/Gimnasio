import { Moment } from 'moment';

export interface IClase {
  id?: number;
  nombre?: string;
  descripcion?: string;
  lugar?: string;
  inicio?: Moment;
  fin?: Moment;
  incidencias?: boolean;
  monitorId?: number;
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
    public monitorId?: number
  ) {
    this.incidencias = this.incidencias || false;
  }
}
