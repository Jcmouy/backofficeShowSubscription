import { IObra } from 'app/shared/model/obra.model';

export interface IEtiqueta {
  id?: number;
  nombre?: string;
  obras?: IObra[];
}

export class Etiqueta implements IEtiqueta {
  constructor(public id?: number, public nombre?: string, public obras?: IObra[]) {}
}
