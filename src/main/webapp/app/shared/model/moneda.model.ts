import { IFuncion } from 'app/shared/model/funcion.model';

export interface IMoneda {
  id?: number;
  codigo?: string;
  nombre?: string;
  funciones?: IFuncion[];
}

export class Moneda implements IMoneda {
  constructor(public id?: number, public codigo?: string, public nombre?: string, public funciones?: IFuncion[]) {}
}
