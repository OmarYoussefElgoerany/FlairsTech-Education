import { Routes } from '@angular/router';
import { CourseComponent } from './course/course.component';
import { CourseFormComponent } from './course-form/course-form.component';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: 'courses', component: CourseComponent },
  { path: 'home', component: HomeComponent },
  { path: 'addCourse', component: CourseFormComponent },
  { path: 'editCourse/:id', component: CourseFormComponent },
];
