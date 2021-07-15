import { Moment } from 'moment';
import { IObra } from 'app/shared/model/obra.model';
import { IPersona } from 'app/shared/model/persona.model';

export interface ICuenta {
  id?: number;
  codigo?: string;
  nombre?: string;
  fechaBaja?: Moment;
  obras?: IObra[];
  personas?: IPersona[];
}

export class Cuenta implements ICuenta {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombre?: string,
    public fechaBaja?: Moment,
    public obras?: IObra[],
    public personas?: IPersona[]
  ) {}
}
