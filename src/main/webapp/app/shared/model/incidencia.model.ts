export interface IIncidencia {
  id?: number;
  nombre?: string;
  descripcion?: string;
  claseId?: number;
}

export class Incidencia implements IIncidencia {
  constructor(public id?: number, public nombre?: string, public descripcion?: string, public claseId?: number) {}
}
