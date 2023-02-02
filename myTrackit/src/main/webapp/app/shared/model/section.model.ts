export interface ISection {
  id?: number;
  sectionNom?: string | null;
  chefSection?: string | null;
  emailChef?: string | null;
  phoneChef?: string | null;
}

export const defaultValue: Readonly<ISection> = {};
