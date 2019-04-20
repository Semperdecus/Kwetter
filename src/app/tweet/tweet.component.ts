import {Component, OnInit} from '@angular/core';
import {TweetService} from '../_services';
import {Tweet} from '../_models/tweet';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.scss']
})
export class TweetComponent implements OnInit {

  public tweets: Tweet[];

  constructor(private tweetService: TweetService) {
  }

  ngOnInit() {
    this.getTweets();
  }

  getTweets() {
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
