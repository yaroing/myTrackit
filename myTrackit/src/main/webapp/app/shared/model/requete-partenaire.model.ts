import dayjs from 'dayjs';
import { IDetailsRequete } from 'app/shared/model/details-requete.model';

export interface IRequetePartenaire {
  id?: number;
  requeteDate?: string | null;
  fichierAtacheContentType?: string | null;
  fichierAtache?: string | null;
  requeteObs?: string | null;
  reqTraitee?: number | null;
  detailsRequetes?: IDetailsRequete[] | null;
}

export const defaultValue: Readonly<IRequetePartenaire> = {};
