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
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
