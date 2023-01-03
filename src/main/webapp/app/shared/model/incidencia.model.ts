export interface IIncidencia {
  id?: number;
  nombre?: string;
  descripcion?: string;
  claseId?: number;
  claseNombre?: string;
}

export class Incidencia implements IIncidencia {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public claseId?: number,
    public claseNombre?: string
  ) {}
}
