import {Account} from './account';

/**
 * @param account:Account
 */
export class Tweet {
  public id: number;
  public message: string;
  public date: string;
  public account: Account;
}
