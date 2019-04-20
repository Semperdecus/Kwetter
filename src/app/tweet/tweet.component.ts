import {Component, OnInit} from '@angular/core';
import {TweetService} from '../_services';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.scss']
})
export class TweetComponent implements OnInit {

  public tweets;

  constructor(private tweetService: TweetService) {
  }

  ngOnInit(): void {
    this.getTweets();
  }

  getTweets() {
    this.tweetService.getAll().subscribe(
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
