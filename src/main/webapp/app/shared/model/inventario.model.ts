import { Moment } from 'moment';

export interface IInventario {
  id?: number;
  ref?: string;
  nombre?: string;
  descripcion?: string;
  cantidad?: number;
  estado?: boolean;
  ultRevision?: Moment;
  periodoRevision?: number;
  proveedorId?: number;
}

export class Inventario implements IInventario {
  constructor(
    public id?: number,
    public ref?: string,
    public nombre?: string,
    public descripcion?: string,
    public cantidad?: number,
    public estado?: boolean,
    public ultRevision?: Moment,
    public periodoRevision?: number,
    public proveedorId?: number
  ) {
    this.estado = this.estado || false;
  }
}
