import {Component, OnInit} from '@angular/core';
import {AccountService, AuthService} from '../_services';

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrls: ['./profile-menu.component.scss']
})
export class ProfileMenuComponent implements OnInit {

  public account;

  constructor(private accountService: AccountService, private authService: AuthService) {
  }

  ngOnInit() {
    const username = localStorage.getItem('currentUser');
    this.getAccount(username);
  }

  getAccount(username) {
    this.accountService.getByUsername(username).subscribe(
      (data) => {
        this.account = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  signOut() {
    this.authService.logout();
  }
}
