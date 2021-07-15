import { Moment } from 'moment';

export interface IPago {
  id?: number;
  idExterno?: string;
  fechaExterna?: Moment;
  subscripcionId?: number;
}

export class Pago implements IPago {
  constructor(public id?: number, public idExterno?: string, public fechaExterna?: Moment, public subscripcionId?: number) {}
}
