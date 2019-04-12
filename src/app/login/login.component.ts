import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../_services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router) {
    this.loginForm = this.fb.group({
      loginForm: ['', Validators.required],
      password: ['', Validators.required],
      username: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.authService.logout();
  }

  login() {
    const val = this.loginForm.value;
    console.log('Logging in');
    if (val.username && val.password) {
      this.authService.login(val.username, val.password)
        .subscribe(
          (data) => {
            console.log(data);

            if (data && data.token) {
              localStorage.setItem('currentUser', JSON.stringify(data));

              this.router.navigateByUrl('/user');
            }
          }
        );
    }
  }
}
