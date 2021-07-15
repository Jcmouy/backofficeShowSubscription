import { Moment } from 'moment';

export interface IProceso {
  id?: number;
  tipo?: string;
  fecha?: Moment;
}

export class Proceso implements IProceso {
  constructor(public id?: number, public tipo?: string, public fecha?: Moment) {}
}
