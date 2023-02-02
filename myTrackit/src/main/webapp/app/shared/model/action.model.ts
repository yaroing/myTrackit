import dayjs from 'dayjs';
import { ITransfert } from 'app/shared/model/transfert.model';

export interface IAction {
  id?: number;
  dateAction?: string | null;
  rapportAction?: string | null;
  transfert?: ITransfert | null;
}

export const defaultValue: Readonly<IAction> = {};
