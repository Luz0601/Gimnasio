export interface IPuesto {
  id?: number;
  nombre?: string;
  horario?: string;
  salario?: number;
}

export class Puesto implements IPuesto {
  constructor(public id?: number, public nombre?: string, public horario?: string, public salario?: number) {}
}
