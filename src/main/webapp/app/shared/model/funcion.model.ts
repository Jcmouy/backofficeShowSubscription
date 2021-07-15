import { Moment } from 'moment';
import { ISubscripcion } from 'app/shared/model/subscripcion.model';

export interface IFuncion {
  id?: number;
  fecha?: Moment;
  precio?: number;
  subscripciones?: ISubscripcion[];
  obraNombre?: string;
  obraId?: number;
  paisNombre?: string;
  paisId?: number;
  monedaNombre?: string;
  monedaId?: number;
}

export class Funcion implements IFuncion {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public precio?: number,
    public subscripciones?: ISubscripcion[],
    public obraNombre?: string,
    public obraId?: number,
    public paisNombre?: string,
    public paisId?: number,
    public monedaNombre?: string,
    public monedaId?: number
  ) {}
}
