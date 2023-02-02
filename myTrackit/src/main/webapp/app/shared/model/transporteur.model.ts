export interface ITransporteur {
  id?: number;
  nomTransporteur?: string | null;
  nomDirecteur?: string | null;
  phoneTransporteur?: string | null;
  emailTransporteur?: string | null;
}

export const defaultValue: Readonly<ITransporteur> = {};
