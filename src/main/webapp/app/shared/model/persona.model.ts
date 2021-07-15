import { Moment } from 'moment';
import { ISubscripcion } from 'app/shared/model/subscripcion.model';

export interface IPersona {
  id?: number;
  codigo?: string;
  nombres?: string;
  apellidos?: string;
  fechaNacimiento?: Moment;
  correoElectronico?: string;
  telefono?: string;
  usuarioId?: number;
  subscripciones?: ISubscripcion[];
  paisId?: number;
  cuentaNombre?: string;
  cuentaId?: number;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombres?: string,
    public apellidos?: string,
    public fechaNacimiento?: Moment,
    public correoElectronico?: string,
    public telefono?: string,
    public usuarioId?: number,
    public subscripciones?: ISubscripcion[],
    public paisId?: number,
    public cuentaNombre?: string,
    public cuentaId?: number
  ) {}
}
