export interface ICatalogue {
  id?: number;
  materialCode?: string | null;
  materialDesc?: string | null;
  materialGroup?: string | null;
}

export const defaultValue: Readonly<ICatalogue> = {};
