import { Moment } from 'moment';

export interface IClienteSuscripcion {
  id?: number;
  ultimoPago?: Moment;
  metodoPago?: string;
  clienteId?: number;
  suscripcionId?: number;
}

export class ClienteSuscripcion implements IClienteSuscripcion {
  constructor(
    public id?: number,
    public ultimoPago?: Moment,
    public metodoPago?: string,
    public clienteId?: number,
    public suscripcionId?: number
  ) {}
}
