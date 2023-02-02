export interface IPointFocalPartenaire {
  id?: number;
  nomPf?: string | null;
  fonctionPf?: string | null;
  gsmPf?: string | null;
  emailPf?: string | null;
}

export const defaultValue: Readonly<IPointFocalPartenaire> = {};
