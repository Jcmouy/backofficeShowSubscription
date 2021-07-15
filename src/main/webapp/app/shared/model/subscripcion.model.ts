import { Moment } from 'moment';
import { MetodoPago } from 'app/shared/model/enumerations/metodo-pago.model';

export interface ISubscripcion {
  id?: number;
  fecha?: Moment;
  metodoPago?: MetodoPago;
  pagoId?: number;
  funcionId?: number;
  personaId?: number;
}

export class Subscripcion implements ISubscripcion {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public metodoPago?: MetodoPago,
    public pagoId?: number,
    public funcionId?: number,
    public personaId?: number
  ) {}
}
