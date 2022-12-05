export interface IProveedor {
  id?: number;
  nombre?: string;
  telefono?: number;
  email?: string;
}

export class Proveedor implements IProveedor {
  constructor(public id?: number, public nombre?: string, public telefono?: number, public email?: string) {}
}
