export const enum PeriodoSuscripcion {
  Mensual = 'Mensual',
  Trimestral = 'Trimestral',
  Anual = 'Anual'
}

export interface ISuscripcion {
  id?: number;
  precio?: number;
  periodo?: PeriodoSuscripcion;
}

export class Suscripcion implements ISuscripcion {
  constructor(public id?: number, public precio?: number, public periodo?: PeriodoSuscripcion) {}
}
