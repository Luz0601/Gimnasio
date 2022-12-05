export interface IClaseCliente {
  id?: number;
  clienteId?: number;
  claseId?: number;
}

export class ClaseCliente implements IClaseCliente {
  constructor(public id?: number, public clienteId?: number, public claseId?: number) {}
}
