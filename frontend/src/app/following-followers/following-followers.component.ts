import {Component, OnInit} from '@angular/core';
import {AccountService, AuthService} from '../_services';

@Component({
  selector: 'app-following-followers',
  templateUrl: './following-followers.component.html',
  styleUrls: ['./following-followers.component.scss']
})
export class FollowingFollowersComponent implements OnInit {
  public followers: Account[];
  public following: Account[];
  public followerAmount = 0;
  public followingAmount = 0;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    this.getFollowingFollowers();
  }

  private getFollowingFollowers() {
    if (AuthService.isLoggedIn() === false) {
      return null;
    } else {
      this.accountService.getFollowing(AuthService.getUser()).subscribe(
        (data) => {
          this.following = data;
          this.followingAmount = data.length;

          this.accountService.getFollowers(AuthService.getUser()).subscribe(
            (data2) => {
              this.followers = data2;
              this.followerAmount = data2.length;
            },
            (error) => {
              console.log(error);
            });
        },
        (error) => {
          console.log(error);
        });
    }
  }
}
