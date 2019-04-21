import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../_services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  returnUrl: string;

  constructor(private fb: FormBuilder,
              private authService: AuthService) {
    this.loginForm = this.fb.group({
      loginForm: ['', Validators.required],
      password: ['', Validators.required],
      username: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.authService.logout();
    this.returnUrl =  '';
  }

  login() {
    const val = this.loginForm.value;
    console.log('Logging in');
    if (val.username && val.password) {
      this.authService.login(val.username, val.password);
    }
  }
}
