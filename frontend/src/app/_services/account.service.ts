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

  search(username) {
    return this.http.get<Account[]>('http://localhost:8080/Kwetter/api/account/search/?username=' + username);
  }

  addFollowing(followingId, id) {
    return this.http.put<Account[]>('http://localhost:8080/Kwetter/api/account/' + id + '/following/' + followingId, '');
  }

  removeFollowing(followerId, id) {
    return this.http.put<Account[]>('http://localhost:8080/Kwetter/api/account/' + id + '/follower/' + followerId, '');
  }

  update(location, website, bio, email, picture, username) {
    return this.http.put<Account[]>('http://localhost:8080/Kwetter/api/account/update/?location=' + location +
      '&website=' + website + '&bio=' + bio + '&email=' + email + '&picture=' + picture + '&username=' + username, '');
  }
}
