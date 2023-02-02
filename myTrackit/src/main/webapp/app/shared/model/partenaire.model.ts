export interface IPartenaire {
  id?: number;
  nomPartenaire?: string | null;
  autreNom?: string | null;
  logPhone?: string | null;
  emailPartenaire?: string | null;
  locPartenaire?: string | null;
}

export const defaultValue: Readonly<IPartenaire> = {};
