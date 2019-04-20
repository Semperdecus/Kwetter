import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Tweet} from '../_models/tweet';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';

@Injectable()
export class TweetService {
  private BASE_URL = 'http://localhost:8080/Kwetter/api/tweet/';

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
  }

  getAll() {
    return this.http.get<Tweet[]>(this.BASE_URL);
  }

  post(tweet) {
    if (this.authService.isLoggedIn() === false) {
      return null;
    } else {
      return this.http.post<Tweet>(
        this.BASE_URL + '?message=' + tweet + '&username=' + this.authService.getUser(),
        {}).subscribe(
        (res) => {
          this.router.navigateByUrl('/home');
        });
    }
  }
}
