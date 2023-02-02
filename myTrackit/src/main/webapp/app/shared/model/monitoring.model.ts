import { IPointService } from 'app/shared/model/point-service.model';

export interface IMonitoring {
  id?: number;
  atpeAnnee?: number | null;
  atpeMois?: number | null;
  atpeStock?: string | null;
  atpeDispo?: number | null;
  atpeEndom?: number | null;
  atpePerime?: number | null;
  atpeRupture?: string | null;
  atpeNjour?: number | null;
  atpeMagasin?: string | null;
  atpePalette?: string | null;
  atpePosition?: string | null;
  atpeHauteur?: number | null;
  atpePersonnel?: string | null;
  atpeAdmission?: number | null;
  atpeSortie?: number | null;
  atpeGueris?: number | null;
  atpeAbandon?: number | null;
  atpePoids?: number | null;
  atpeTrasnsfert?: number | null;
  atpeParent?: number | null;
  pointService?: IPointService | null;
}

export const defaultValue: Readonly<IMonitoring> = {};
