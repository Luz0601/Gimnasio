import { Moment } from 'moment';
import { PeriodoSuscripcion } from './suscripcion.model';

export interface IClienteSuscripcion {
  id?: number;
  ultimoPago?: Moment;
  metodoPago?: string;
  clienteId?: number;
  clienteNombre?: string;
  suscripcionId?: number;
  suscripcionPeriodo?: PeriodoSuscripcion;
}

export class ClienteSuscripcion implements IClienteSuscripcion {
  constructor(
    public id?: number,
    public ultimoPago?: Moment,
    public metodoPago?: string,
    public clienteId?: number,
    public clienteNombre?: string,
    public suscripcionId?: number,
    public suscripcionPeriodo?: PeriodoSuscripcion
  ) {}
}
