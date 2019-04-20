import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class AccountService {

  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<Account[]>('http://localhost:8080/Kwetter/api/account');
  }

  getByUsername(username) {
    return this.http.get<Account>('http://localhost:8080/Kwetter/api/account/username/?username=' + username);
  }

  getFollowing(username) {
    return this.http.get<Account[]>('http://localhost:8080/Kwetter/api/account/following/?username=' + username);

  }

  getFollowers(username) {
    return this.http.get<Account[]>('http://localhost:8080/Kwetter/api/account/followers/?username=' + username);
  }
}
