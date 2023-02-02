export interface ISuiviMission {
  id?: number;
  problemeConstate?: string | null;
  actionRecommandee?: string | null;
  dateEcheance?: string | null;
}

export const defaultValue: Readonly<ISuiviMission> = {};
