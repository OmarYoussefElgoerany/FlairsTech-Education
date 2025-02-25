import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ICourse } from '../../models/ICourse';
import { CourseService } from '../../services/courses/course.service';

@Component({
  selector: 'app-course-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule], // ✅ Import ReactiveFormsModule
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.css',
})
export class CourseFormComponent implements OnInit {
  courseForm!: FormGroup;
  isEditMode: boolean;
  courseId!: number;
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private courseService: CourseService,
    private activatedRoute: ActivatedRoute
  ) {
    this.isEditMode = false;
  }

  ngOnInit(): void {
    this.initializeForm();
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        const id = params.get('id');
        if (id) {
          this.loadCourseData(+id);
          this.courseId = +id;
          this.isEditMode = true;
        }
      },
    });
  }
  loadCourseData(id: number) {
    this.courseService.getById(id).subscribe({
      next: (course) => {
        console.log(`LOADED COURSE` + course.data);
        this.courseForm.patchValue(course.data);
      },
      error: (err: Error) => console.error(err.message),
    });
  }
  initializeForm() {
    this.courseForm = this.fb.group({
      title: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(20),
        ],
      ],
      description: [
        '',
        [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(100),
        ],
      ],
      instructor: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50),
        ],
      ],
      createdBy: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(50),
        ],
      ],
      category: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(30),
        ],
      ],
      duration: ['', [Validators.required, Validators.min(1)]],
    });
  }

  // ✅ Getters for form controls
  get title() {
    return this.courseForm.get('title');
  }

  get description() {
    return this.courseForm.get('description');
  }
  get createdBy() {
    return this.courseForm.get('createdBy');
  }
  get instructor() {
    return this.courseForm.get('instructor');
  }

  get category() {
    return this.courseForm.get('category');
  }

  get duration() {
    return this.courseForm.get('duration');
  }

  // ✅ Submit method
  onSubmit() {
    if (this.courseForm.valid) {
      if (this.isEditMode) {
        let updateCourse: ICourse = this.courseForm.value as ICourse;
        updateCourse.id = this.courseId;
        console.log(updateCourse);
        this.courseService.update(updateCourse).subscribe({
          next: (resp) =>
            console.log(`${resp.message} course id : ` + resp.data),
          error: (error: Error) =>
            console.error(`Failed to update ` + error.message),
        });
      } else {
        console.log(this.isEditMode + `edit t or f `);
        this.courseService.create(this.courseForm.value as ICourse).subscribe({
          next: (resp) => console.log(`Created Succesfully` + resp.message),
          error: (err: Error) => {
            console.log(this.courseForm.value as ICourse);
            console.error(err.message);
          },
        });
      }
      //this.router.navigateByUrl('/home');
    } else {
      console.log('Form is invalid');
    }
  }
}
