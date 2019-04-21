import {Component, OnInit} from '@angular/core';
import {TweetService, AccountService} from '../_services';
import {Tweet} from '../_models/tweet';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.scss']
})
export class TweetComponent implements OnInit {
  private sub: any;

  public tweets: Tweet[] = [];

  constructor(private tweetService: TweetService, private accountService: AccountService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      if (params.username) {
        this.getTweet(params.username);
      } else {
        this.getFollowingTweets();
      }
    });
  }

  getTweet(username) {
    console.log(username);
    console.log(username);
    console.log(username);
    this.tweetService.getAll().subscribe(
      (data) => {
        data = data.filter(x => x.account.username === username);
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
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
