import { IMission } from 'app/shared/model/mission.model';

export interface IItemVerifie {
  id?: number;
  quantiteTransfert?: number | null;
  quantiteRecu?: number | null;
  quantiteUtilisee?: number | null;
  quantiteDisponible?: number | null;
  quantiteEcart?: number | null;
  mission?: IMission | null;
}

export const defaultValue: Readonly<IItemVerifie> = {};
