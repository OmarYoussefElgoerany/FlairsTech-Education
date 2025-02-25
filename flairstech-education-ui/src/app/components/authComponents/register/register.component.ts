import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  ReactiveFormsModule,
  ValidatorFn,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { IRegisterRequest } from '../../../models/authModels/IRegisterRequest';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  alreadyExist!: boolean;
  registrationFailed!: boolean;

  constructor(
    private fb: FormBuilder,
    private regisServ: AuthService,
    private router: Router
  ) {
    this.registrationFailed = false;
    this.alreadyExist = false;
  }

  ngOnInit(): void {
    this.initializeForm();
  }
  initializeForm() {
    this.registerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      role: ['', Validators.required],
    });

    // Add the validator explicitly
    this.registerForm.addValidators(
      this.mustMatch('password', 'confirmPassword')
    );
  }

  get firstname() {
    return this.registerForm.get('firstname');
  }
  get lastname() {
    return this.registerForm.get('lastname');
  }
  get email() {
    return this.registerForm.get('email');
  }
  get password() {
    return this.registerForm.get('password');
  }
  get confirmPassword() {
    return this.registerForm.get('confirmPassword');
  }
  get role() {
    return this.registerForm.get('role');
  }
  mustMatch(password: string, confirmPassword: string): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const passControl = formGroup.get(password);
      const confirmPassControl = formGroup.get(confirmPassword);

      if (!passControl || !confirmPassControl) {
        return null;
      }

      if (
        confirmPassControl.errors &&
        !confirmPassControl.errors['mustMatch']
      ) {
        return null;
      }

      return passControl.value !== confirmPassControl.value
        ? { mustMatch: true }
        : null;
    };
  }

  registerApi(register: IRegisterRequest) {
    this.regisServ.register(register).subscribe({
      next: (resp) => {
        this.router.navigateByUrl('/login');
      },
      error: (err: ErrorEvent) => {
        console.log(`${err.error.error}`);
        if (
          err.error.error.includes(
            'duplicate key value violates unique constraint'
          )
        ) {
          this.alreadyExist = true;
        } else {
          this.registrationFailed = true;
        }
      },
    });
  }
  onSubmit() {
    if (this.registerForm.valid) {
      const request: IRegisterRequest = {
        firstname: this.registerForm.value.firstname,
        lastname: this.registerForm.value.lastname,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password,
        role: this.registerForm.value.role,
      };
      console.log('Register Request:', request);
      // Send request to API
      this.registerApi(this.registerForm.value as IRegisterRequest);
      console.log(this.registerForm);
    }
  }
}
