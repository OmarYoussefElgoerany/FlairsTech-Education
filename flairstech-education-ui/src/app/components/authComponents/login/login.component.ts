import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../services/auth/auth.service';
import { ILoginRequest } from '../../../models/authModels/ILoginRequest';
import { CommonModule } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  notExist: boolean = false;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authServ: AuthService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  // ✅ Getters for form controls
  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
  loginApi() {
    let x = this.loginForm.value as ILoginRequest;

    this.authServ.login(this.loginForm.value as ILoginRequest).subscribe({
      next: (res) => {
        this.authServ.setAuthToken(res.token);
        this.notExist = false;
        this.router.navigateByUrl('/home');

        console.log(res.token);
      },
      error: (err) => {
        this.notExist = true;
        console.error(err.message);
      },
    });
  }
  // ✅ Submit method
  onSubmit() {
    if (this.loginForm.valid) {
      console.log('Logging in...', this.loginForm.value);
      this.loginApi();
    } else {
      console.log('Form is invalid');
    }
  }
}
