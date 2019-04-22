import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AccountService, AuthService} from '../_services';

@Component({
  selector: 'app-option-bar',
  templateUrl: './option-bar.component.html',
  styleUrls: ['./option-bar.component.scss']
})
export class OptionBarComponent implements OnInit {
  following: boolean;
  hidden: boolean;
  private sub: any;
  private profile;
  private loggedUser;
  public followingList;

  constructor(private route: ActivatedRoute, private accountService: AccountService, private authService: AuthService) {
    this.following = false;
  }

  ngOnInit() {
    // see if user is logged in and check follow status
    if (AuthService.isLoggedIn() === false) {
      this.hidden = true;
    } else {
      this.sub = this.route.params.subscribe(params => {
        if (params.username) {
          this.hidden = false;
          this.accountService.getByUsername(params.username).subscribe((data) => {
            this.profile = data;
            console.log(this.profile);
            this.accountService.getByUsername(AuthService.getUser()).subscribe((data2) => {
              this.loggedUser = data2;
              console.log(this.loggedUser);
              this.refreshFollowStatus();
            });
          });
        }
      });
    }
  }

  refreshFollowStatus() {
    this.accountService.getFollowing(AuthService.getUser()).subscribe(
      (data) => {
        this.followingList = data;
        if (this.followingList.some(e => e.username === this.profile.username)) {
          console.log('TRUUUUUEEEE');
          this.following = true;
        } else {
          console.log('false.');
          this.following = false;
        }
      });
  }

  follow() {
    this.accountService.addFollowing(this.loggedUser.id, this.profile.id).subscribe(
      (data) => {
        this.refreshFollowStatus();
      }
    );
  }

  unfollow() {
    this.accountService.removeFollowing(this.loggedUser.id, this.profile.id).subscribe(
      (data) => {
        this.refreshFollowStatus();
      }
    );
  }
}
