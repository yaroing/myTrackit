import dayjs from 'dayjs';
import { ITransfert } from 'app/shared/model/transfert.model';

export interface IItemTransfert {
  id?: number;
  roDate?: string | null;
  matDesc?: string | null;
  unit?: string | null;
  delQty?: number | null;
  value?: number | null;
  batch?: string | null;
  bbDate?: string | null;
  weight?: number | null;
  volume?: number | null;
  recQty?: number | null;
  transfert?: ITransfert | null;
}

export const defaultValue: Readonly<IItemTransfert> = {};
