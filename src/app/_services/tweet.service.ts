import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Tweet} from '../_models';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';
import {Subject} from 'rxjs';
import {log} from 'util';
import {WebsocketService} from './websocket.service';
import {map} from 'rxjs/internal/operators/map';
import {distinctUntilChanged} from 'rxjs/internal/operators/distinctUntilChanged';

@Injectable()
export class TweetService {
  private BASE_URL = 'http://localhost:8080/Kwetter/api/tweet/';
  public messages: Subject<Tweet>;

  constructor(private wsService: WebsocketService, private http: HttpClient, private router: Router) {
    this.newTweetSocket();
  }

  newTweetSocket() {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      this.messages = this.wsService.connect(
        'ws/newTweet/' + AuthService.getUser()
      ).pipe(map(
        (response: MessageEvent): Tweet => {
          console.log(response);

          const data = JSON.parse(response.data);

          return {
            id: data.id,
            date: data.date,
            account: data.account,
            message: data.message
          };
        })
      ) as Subject<Tweet>;
    }
  }

  post(tweet) {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      return this.http.post<Tweet>(
        this.BASE_URL + '?message=' + tweet + '&username=' + AuthService.getUser(),
        {});
    }
  }

  getAll() {
    return this.http.get<Tweet[]>(this.BASE_URL);
  }

  getFollowingTweet() {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      return this.http.get<Tweet[]>(this.BASE_URL + 'following/?username=' + AuthService.getUser());
    }
  }

  getUserTweet(username) {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      return this.http.get<Tweet[]>(this.BASE_URL + 'username/?username=' + username);
    }
  }



  search(message) {
    return this.http.get<Tweet[]>(this.BASE_URL + 'search/?message=' + message);
  }

  delete(tweet) {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      return this.http.delete<Tweet>(
        this.BASE_URL + tweet.id,
        {}).subscribe(
        () => {
          window.location.reload();
        });
    }
  }
}
