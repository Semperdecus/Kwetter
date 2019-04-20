import { Role } from './role';

export class Account {
  public id: number;
  public username: string;
  public password: string;
  public email: string;
  public location: string;
  public website: string;
  public picture: string;
  public bio: string;
  public role: Role;
}
