export interface IStaff {
  id?: number;
  staffFname?: string | null;
  staffLname?: string | null;
  staffTitle?: string | null;
  staffName?: string | null;
  staffEmail?: string | null;
  staffPhone?: string | null;
}

export const defaultValue: Readonly<IStaff> = {};
