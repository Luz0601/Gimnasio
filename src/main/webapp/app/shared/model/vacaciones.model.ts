import { Moment } from 'moment';

export interface IVacaciones {
  id?: number;
  inicio?: Moment;
  fin?: Moment;
  empleadoId?: number;
}

export class Vacaciones implements IVacaciones {
  constructor(public id?: number, public inicio?: Moment, public fin?: Moment, public empleadoId?: number) {}
}
