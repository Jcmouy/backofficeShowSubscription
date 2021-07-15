import { IObra } from 'app/shared/model/obra.model';
import { TiposDeObra } from 'app/shared/model/enumerations/tipos-de-obra.model';

export interface ITipoDeObra {
  id?: number;
  tipo?: TiposDeObra;
  descripcion?: string;
  obras?: IObra[];
}

export class TipoDeObra implements ITipoDeObra {
  constructor(public id?: number, public tipo?: TiposDeObra, public descripcion?: string, public obras?: IObra[]) {}
}
