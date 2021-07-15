export interface IContenido {
  id?: number;
  indice?: string;
  subindice?: string;
  tipoContenido?: string;
  valor?: string;
  resumen?: string;
  obraNombre?: string;
  obraId?: number;
}

export class Contenido implements IContenido {
  constructor(
    public id?: number,
    public indice?: string,
    public subindice?: string,
    public tipoContenido?: string,
    public valor?: string,
    public resumen?: string,
    public obraNombre?: string,
    public obraId?: number
  ) {}
}
