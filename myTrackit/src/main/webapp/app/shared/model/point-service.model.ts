import { IMonitoring } from 'app/shared/model/monitoring.model';
import { IRequetePointService } from 'app/shared/model/requete-point-service.model';
import { IStockPointService } from 'app/shared/model/stock-point-service.model';

export interface IPointService {
  id?: number;
  nomPos?: string | null;
  posLon?: number | null;
  posLat?: number | null;
  posContact?: string | null;
  posGsm?: string | null;
  monitorings?: IMonitoring[] | null;
  requetePointServices?: IRequetePointService[] | null;
  stockPointServices?: IStockPointService[] | null;
}

export const defaultValue: Readonly<IPointService> = {};
