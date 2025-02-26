import { Routes } from '@angular/router';
import { CourseFormComponent } from './components/course-form/course-form.component';
import { CourseComponent } from './components/course/course.component';
import { HomeComponent } from './components/home/home.component';
import { authGuard } from './guards/auth.guard';
import { LoginComponent } from './components/authComponents/login/login.component';
import { RegisterComponent } from './components/authComponents/register/register.component';
import { ErrorComponent } from './components/error/error.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'courses', component: CourseComponent, canActivate: [authGuard] },
  {
    path: 'addCourse',
    component: CourseFormComponent,
    canActivate: [authGuard],
  },
  {
    path: 'editCourse/:id',
    component: CourseFormComponent,
    canActivate: [authGuard],
  },
  // {
  //   path: '',
  //   redirectTo: 'home',
  //   pathMatch: 'full',
  //   canActivate: [authGuard],
  // },
  // { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  // { path: 'login', component: LoginComponent },
  // { path: 'register', component: RegisterComponent },
  // { path: 'courses', component: CourseComponent, canActivate: [authGuard] },
  // {
  //   path: 'addCourse',
  //   component: CourseFormComponent,
  //   canActivate: [authGuard],
  // },
  // {
  //   path: 'editCourse/:id',
  //   component: CourseFormComponent,
  //   canActivate: [authGuard],
  // },,
  {
    path: '**',
    component: ErrorComponent,
  },
];
