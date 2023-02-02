import { IRequetePartenaire } from 'app/shared/model/requete-partenaire.model';

export interface IDetailsRequete {
  id?: number;
  quantiteDemandee?: number | null;
  quantiteApprouvee?: number | null;
  quantiteRecue?: number | null;
  itemObs?: string | null;
  requetePartenaire?: IRequetePartenaire | null;
}

export const defaultValue: Readonly<IDetailsRequete> = {};
