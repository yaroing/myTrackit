import { IPointService } from 'app/shared/model/point-service.model';

export interface IStockPointService {
  id?: number;
  stockAnnee?: string | null;
  stockMois?: string | null;
  entreeMois?: number | null;
  sortieMois?: number | null;
  stockFinmois?: number | null;
  stockDebut?: number | null;
  pointService?: IPointService | null;
}

export const defaultValue: Readonly<IStockPointService> = {};
