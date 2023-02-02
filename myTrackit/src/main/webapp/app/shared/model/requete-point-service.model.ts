import dayjs from 'dayjs';
import { IPointService } from 'app/shared/model/point-service.model';

export interface IRequetePointService {
  id?: number;
  stockDisponible?: number | null;
  quantDem?: number | null;
  quantTrs?: number | null;
  quantRec?: number | null;
  reqTraitee?: number | null;
  dateReq?: string | null;
  dateRec?: string | null;
  dateTransfert?: string | null;
  pointService?: IPointService | null;
}

export const defaultValue: Readonly<IRequetePointService> = {};
