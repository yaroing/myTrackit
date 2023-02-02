export interface IStockPartenaire {
  id?: number;
  stockAnnee?: string | null;
  stockMois?: string | null;
  entreeMois?: number | null;
  sortieMois?: number | null;
  stockFinmois?: number | null;
  stockDebut?: number | null;
}

export const defaultValue: Readonly<IStockPartenaire> = {};
