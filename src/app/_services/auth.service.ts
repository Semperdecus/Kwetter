import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import * as moment from 'moment';
import {Router} from '@angular/router';

@Injectable()
export class AuthService {

  constructor(private http: HttpClient, private router: Router) {
  }

  login(username: string, password: string) {
    return this.http.post<any>(
      'http://localhost:8080/Kwetter/api/auth/login',
      {username, password}).subscribe(
      (res) => {
        localStorage.setItem('currentUser', username);
        this.setSession(res);
        this.router.navigateByUrl('/home');
      }
    );
  }

  private setSession(authResult) {
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    localStorage.setItem('token', authResult.token);
    localStorage.setItem('expires_at', JSON.stringify(expiresAt.valueOf()));
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    localStorage.removeItem('expires_at');
  }

  // methods to see if user is logged in
  public isLoggedIn() {
    console.log(this.getExpiration());
    console.log(moment())
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt).add(7, 'days');
  }

  getUser() {
    return localStorage.getItem('currentUser');
  }
}
