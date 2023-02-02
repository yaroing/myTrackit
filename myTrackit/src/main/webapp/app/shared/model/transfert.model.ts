import dayjs from 'dayjs';
import { IAction } from 'app/shared/model/action.model';
import { IItemTransfert } from 'app/shared/model/item-transfert.model';

export interface ITransfert {
  id?: number;
  dateExp?: string | null;
  nomChauffeur?: string | null;
  dateRec?: string | null;
  cphone?: string | null;
  actions?: IAction[] | null;
  itemTransferts?: IItemTransfert[] | null;
}

export const defaultValue: Readonly<ITransfert> = {};
