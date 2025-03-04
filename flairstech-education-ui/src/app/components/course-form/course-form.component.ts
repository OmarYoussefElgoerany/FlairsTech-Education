import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ICourse } from '../../models/ICourse';
import { CourseService } from '../../services/courses/course.service';
import { DialogElement } from '../dialog/dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { SuccessDialogComponent } from '../success-dialog/success-dialog.component';

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
  isSuceess: boolean;
  error: boolean;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private courseService: CourseService,
    private activatedRoute: ActivatedRoute,
    private dialog: MatDialog
  ) {
    this.isEditMode = false;
    this.isSuceess = false;
    this.error = false;
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
      duration: [
        '',
        [
          Validators.required,
          Validators.min(1),
          Validators.max(120),
          Validators.pattern(/^1\d{0,2}$/), // Must start with '1' and have up to 3 digits
        ],
      ],
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
          next: (resp) => {
            this.isSuceess = true;
            this.openDialog('Success', 'Form submitted successfully!', true);
          },
          error: (err: ErrorEvent) => {
            console.error(`Failed to update ` + err.error);

            console.error(`Failed to update ` + err.error.validationErrors[0]);
            this.openDialog(
              'Error',
              `Form submission failed Check ${err.error.validationErrors[0]}. Please try again.`,

              false
            );
          },
        });
      } else {
        this.courseService.create(this.courseForm.value as ICourse).subscribe({
          next: (resp) => {
            this.openDialog('Success', 'Form submitted successfully!', true);
            this.isSuceess = true;
          },
          error: (err: ErrorEvent) => {
            console.log(this.courseForm.value as ICourse);
            this.openDialog(
              'Error',
              `Form submission failed Check ${err.error.validationErrors[0]}. Please try again.`,

              false
            );
          },
        });
      }
    } else {
    }
  }

  openDialog(title: string, message: string, isSuccess: boolean) {
    const dialogRef = this.dialog.open(SuccessDialogComponent, {
      width: '300px',
      data: { title, message },
    });

    dialogRef.afterClosed().subscribe(() => {
      if (isSuccess && this.isEditMode) {
        this.router.navigate(['/courses']); // Navigate on success
      } else if (isSuccess) {
        this.courseForm.reset();
      } else {
        this.error = true;
      }
    });
  }
}
