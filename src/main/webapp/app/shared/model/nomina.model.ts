export const enum TipoNomina {
  Temporal = 'Temporal',
  Indefinido = 'Indefinido',
  Practicas = 'Practicas'
}

export interface INomina {
  id?: number;
  iban?: string;
  tipoContrato?: TipoNomina;
}

export class Nomina implements INomina {
  constructor(public id?: number, public iban?: string, public tipoContrato?: TipoNomina) {}
}
