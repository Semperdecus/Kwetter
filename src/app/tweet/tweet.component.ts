import {Component, Input, OnInit} from '@angular/core';
import {TweetService, AccountService, AuthService} from '../_services';
import {Tweet} from '../_models';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.scss']
})
export class TweetComponent implements OnInit {
  @Input() profileSettings;
  private sub: any;
  private loggedInUser: string;
  public tweets: Tweet[] = [];
  successAlert: boolean;

  constructor(private tweetService: TweetService, private accountService: AccountService, private route: ActivatedRoute) {
    this.successAlert = true;

    tweetService.messages.subscribe(msg => {
      this.tweets.unshift(msg);
      this.successAlert = false;
    });
  }

  ngOnInit() {
    this.loggedInUser = AuthService.getUser();
    if (!this.profileSettings) {
      this.sub = this.route.params.subscribe(params => {
        if (params.username) {
          this.getTweet(params.username);
        } else {
          this.getFollowingTweets();
        }
      });
    } else {
      this.getTweet(AuthService.getUser());
    }
  }

  getTweet(username) {
    this.tweetService.getUserTweet(username).subscribe(
      (data) => {
        this.tweets = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  getFollowingTweets() {
    this.tweetService.getFollowingTweet().subscribe(
      (data) => {
        this.tweets = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  delete(tweet) {
    this.tweetService.delete(tweet);
  }
}
