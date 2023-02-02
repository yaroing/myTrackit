import dayjs from 'dayjs';
import { IItemVerifie } from 'app/shared/model/item-verifie.model';

export interface IMission {
  id?: number;
  dateMission?: string | null;
  dateDebut?: string | null;
  dateFin?: string | null;
  rapportMissionContentType?: string | null;
  rapportMission?: string | null;
  debutMission?: string | null;
  finMission?: string | null;
  field10?: string | null;
  fin?: string | null;
  itemVerifies?: IItemVerifie[] | null;
}

export const defaultValue: Readonly<IMission> = {};
