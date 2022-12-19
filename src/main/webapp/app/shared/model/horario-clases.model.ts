export interface IHorarioClases {
  claseId?: number;
  claseNombre?: string;
  monitorId?: number;
  monitorNombre?: string;
} // ng g c entities/horario-clase --save

export class HorarioClases implements IHorarioClases {}
