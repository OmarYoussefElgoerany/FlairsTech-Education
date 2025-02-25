import {
  ChangeDetectorRef,
  Component,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { PaginatorComponent } from '../paginator/paginator.component';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ICourse } from '../../models/ICourse';
import { CourseService } from '../../services/courses/course.service';

@Component({
  selector: 'app-course',
  standalone: true,
  imports: [PaginatorComponent, CommonModule, RouterLink, FormsModule],
  templateUrl: './course.component.html',
  styleUrl: './course.component.css',
})
export class CourseComponent implements OnInit, OnChanges {
  courses!: ICourse[];
  page: number = 0; // page index
  size: number = 5; // page size
  totalPages: number = 2; // total pages
  totalCourses!: number;
  courseId!: number;
  searchingVal!: string;
  constructor(private courseService: CourseService, private router: Router) {}
  ngOnChanges(changes: SimpleChanges): void {
    this.courses.filter((course) => this.courseId != course.id);
  }

  ngOnInit(): void {
    this.fetchCourses();
  }
  search() {
    console.log(this.searchingVal);
    this.courseService.getByTitle(this.searchingVal).subscribe({
      next: (resp) => {
        this.courses = resp.data;
      },
      error: (err) => console.error(`Error Message : ` + err.message``),
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
      },

      error: (error: Error) => console.error(`resp : ${error.message}`),
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
}
