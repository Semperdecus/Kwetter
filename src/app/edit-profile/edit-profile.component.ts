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
  successAlert: boolean;
  warningAlert: boolean;
  loggedAccount: Account;

  constructor(private fb: FormBuilder,
              private accountService: AccountService) {
    this.successAlert = true;
    this.warningAlert = true;
    this.accountForm = this.fb.group({
      email: [''],
      location: [''],
      bio: [''],
      website: [''],
      picture: ['']
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
        website: [this.loggedAccount.website],
        picture: [this.loggedAccount.picture]
      });
    });
  }

  update() {
    const val = this.accountForm.value;
    console.log(val);
    if (val.bio.length > 140) {
      this.warningAlert = false;
    } else {
      this.accountService.update(val.location, val.website, val.bio, val.email, val.picture, AuthService.getUser()).subscribe(() => {
        this.successAlert = false;
      });
    }

  }
}
