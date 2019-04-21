import {Role} from './role';
import {Tweet} from './tweet';

/**
 * @param picture:string
 * @param username:string
 */
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
  public tweet: Tweet;
}
