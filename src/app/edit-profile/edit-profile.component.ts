import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AccountService, AuthService} from '../_services';
import {Account} from '../_models';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  accountForm: FormGroup;
  staticAlertClosed: boolean;
  loggedAccount: Account;

  constructor(private fb: FormBuilder,
              private accountService: AccountService) {
    this.staticAlertClosed = true;
    this.accountForm = this.fb.group({
      email: [''],
      location: [''],
      bio: [''],
      website: ['']
    });
  }

  ngOnInit() {
    this.accountService.getByUsername(AuthService.getUser()).subscribe(data => {
      // @ts-ignore
      this.loggedAccount = data;
      console.log(data);
      this.accountForm = this.fb.group({
        email: [this.loggedAccount.email],
        location: [this.loggedAccount.location],
        bio: [this.loggedAccount.bio],
        website: [this.loggedAccount.website]
      });
    });
  }

  update() {
    const val = this.accountForm.value;
    console.log(val);
    this.accountService.update(val.location, val.website, val.bio, val.email, AuthService.getUser()).subscribe(() => {
      this.staticAlertClosed = false;
    });
  }
}
