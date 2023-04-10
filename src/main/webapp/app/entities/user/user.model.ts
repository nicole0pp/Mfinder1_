export interface IUser {
  id: number | null;
  login?: string;
}

export class User implements IUser {
  constructor(public id: number, public login: string) {}
}

export function getUserIdentifier(user: IUser): number | null {
  return user.id;
}
