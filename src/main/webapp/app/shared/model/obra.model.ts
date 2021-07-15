import { Moment } from 'moment';
import { IFuncion } from 'app/shared/model/funcion.model';
import { IContenido } from 'app/shared/model/contenido.model';
import { IEtiqueta } from 'app/shared/model/etiqueta.model';

export interface IObra {
  id?: number;
  nombre?: string;
  descripcion?: string;
  imagenContentType?: string;
  imagen?: any;
  iconoContentType?: string;
  icono?: any;
  protagonistas?: string;
  direccion?: string;
  autores?: string;
  fecha?: Moment;
  duracion?: string;
  funciones?: IFuncion[];
  contenidos?: IContenido[];
  etiquetas?: IEtiqueta[];
  tipoTipo?: string;
  tipoId?: number;
  cuentaNombre?: string;
  cuentaId?: number;
}

export class Obra implements IObra {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public imagenContentType?: string,
    public imagen?: any,
    public iconoContentType?: string,
    public icono?: any,
    public protagonistas?: string,
    public direccion?: string,
    public autores?: string,
    public fecha?: Moment,
    public duracion?: string,
    public funciones?: IFuncion[],
    public contenidos?: IContenido[],
    public etiquetas?: IEtiqueta[],
    public tipoTipo?: string,
    public tipoId?: number,
    public cuentaNombre?: string,
    public cuentaId?: number
  ) {}
}
