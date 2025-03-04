import { Component, OnDestroy, OnInit } from '@angular/core';
import { PaginatorComponent } from '../paginator/paginator.component';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ICourse } from '../../models/ICourse';
import { CourseService } from '../../services/courses/course.service';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  switchMap,
  takeUntil,
} from 'rxjs/operators';
import { of, Subject } from 'rxjs';

@Component({
  selector: 'app-course',
  standalone: true,
  imports: [
    PaginatorComponent,
    CommonModule,
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './course.component.html',
  styleUrl: './course.component.css',
})
export class CourseComponent implements OnInit, OnDestroy {
  courses!: ICourse[];
  page: number = 0; // page index
  size: number = 5; // page size
  totalPages: number = 2; // total pages
  totalCourses!: number;
  courseId!: number;
  searchingVal!: string;
  cachedList!: ICourse[];
  formControl: FormControl;
  private destroy$: Subject<void>;
  intervalId: any;
  constructor(private courseService: CourseService, private router: Router) {
    this.formControl = new FormControl('');
    this.destroy$ = new Subject<void>();
  }

  ngOnInit(): void {
    this.fetchCourses();
    this.formControl.valueChanges
      .pipe(
        debounceTime(500),
        distinctUntilChanged(),
        switchMap((val) =>
          this.courseService.getByTitle((val as string) || '').pipe(
            catchError((err) => {
              if (!val) {
                return of({ data: this.cachedList });
              }
              return of({ data: [] });
            })
          )
        ),
        takeUntil(this.destroy$)
      )
      .subscribe({
        next: (resp) => {
          this.courses = resp.data;
          console.log(`Sucess Response data:` + resp.data);
        },
        error: (err) => console.log(`ERROR HAPPEN IN API ${err.message}`),
      });
  }

  fetchCourses(): void {
    this.courseService.getAll(this.page, this.size).subscribe({
      next: (courses) => {
        console.log(`API Success + length ${courses.content.length}`);
        this.courses = courses.content;
        this.page = courses.number;
        this.size = courses.size;
        this.totalPages = courses.totalPages;
        this.totalCourses = courses.totalElements; // Assuming the API provides total count
        this.totalPages = Math.ceil(this.totalCourses / this.size);
        this.cachedList = courses.content;
      },
      error: (error) => {
        console.error('API Error:', error);
      },
    });
  }

  delete(id: number) {
    this.courseService.delete(id).subscribe({
      next: (resp) => {
        console.log(resp.message);
        this.courses = this.courses.filter((course) => course.id != id);
        if (this.courses.length === 0) {
          this.loadNextPage();
        }
        console.log('Next');
      },

      error: (error: Error) => console.error(`resp : ${error.message}`),
    });
  }
  loadNextPage() {
    if (this.page != 0) {
      this.page++; // Increment the page
      this.totalPages = this.courses.length;
    }
    this.courseService.getAll(this.page, this.size).subscribe({
      next: (newCourses) => {
        this.courses = newCourses.content;
      },
      error: (err) => console.error('Error fetching next page:', err),
    });
  }
  handlePagination($event: {
    length: number;
    pageSize: number;
    pageIndex: number;
  }) {
    console.log(
      `new valuesss ${$event.length} ${$event.pageIndex} ${$event.pageSize}`
    );
    this.page = $event.pageIndex;
    this.size = $event.pageSize;
    this.totalPages = $event.length;

    this.fetchCourses();
  }
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
