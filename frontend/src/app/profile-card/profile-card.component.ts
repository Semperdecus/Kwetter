import {Component, OnInit} from '@angular/core';
import {AccountService} from '../_services';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-profile-card',
  templateUrl: './profile-card.component.html',
  styleUrls: ['./profile-card.component.scss']
})
export class ProfileCardComponent implements OnInit {
  private sub: any;
  public account;

  constructor(private accountService: AccountService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      if (params.username) {
        this.getAccount(params.username);
      } else {
        const username = localStorage.getItem('currentUser');
        this.getAccount(username);
      }
    });
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
