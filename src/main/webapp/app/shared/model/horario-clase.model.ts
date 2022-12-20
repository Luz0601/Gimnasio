import { Moment } from 'moment';
import { ICliente } from './cliente.model';

export interface IHorarioClase {
  claseId?: number;
  claseNombre?: string;
  monitorId?: number;
  monitorNombre?: string;
  inicio?: Moment;
  fin?: Moment;
  clientes?: ICliente[];
  numClientes?: number;
  maxClientes?: number;
} // ng g c entities/horario-clase --save

export class HorarioClase implements IHorarioClase {
  constructor(
    public claseId?: number,
    public claseNombre?: string,
    public monitorId?: number,
    public monitorNombre?: string,
    public inicio?: Moment,
    public fin?: Moment,
    public clientes?: ICliente[],
    public numClientes?: number,
    public maxClientes?: number
  ) {}
}
