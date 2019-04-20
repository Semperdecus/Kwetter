import {Component, OnInit} from '@angular/core';
import {AccountService} from '../_services';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.scss']
})
export class ProfileCardComponent implements OnInit {

  public account;

  constructor(private accountService: AccountService) {
  }

  ngOnInit() {
    const username = localStorage.getItem('currentUser');
    this.getAccount(username);
  }

  getAccount(username) {
    this.accountService.getByUsername(username).subscribe(
      (data) => {
        this.account = data;
        console.log(this.account);
        if (this.account.picture == null) {
          this.account.picture = 'https://i.pinimg.com/originals/9f/81/2d/9f812d4cf313e887ef99d8722229eee1.jpg';
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
