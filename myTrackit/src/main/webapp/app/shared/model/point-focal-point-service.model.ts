export interface IPointFocalPointService {
  id?: number;
  nomPf?: string | null;
  fonctionPf?: string | null;
  gsmPf?: string | null;
  emailPf?: string | null;
}

export const defaultValue: Readonly<IPointFocalPointService> = {};
