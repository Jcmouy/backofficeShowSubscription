import { IPersona } from 'app/shared/model/persona.model';
import { IFuncion } from 'app/shared/model/funcion.model';

export interface IPais {
  id?: number;
  codigo?: string;
  nombre?: string;
  personas?: IPersona[];
  funciones?: IFuncion[];
}

export class Pais implements IPais {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombre?: string,
    public personas?: IPersona[],
    public funciones?: IFuncion[]
  ) {}
}
