import { IGenericResponse } from './../../common/IGenericResponse';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICourse } from '../../models/ICourse';
import { IPageResponse } from '../../common/IPageResponse';
import { enviroment } from '../../enviroments/enviroment';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  course: string;
  constructor(private HttpClient: HttpClient) {
    this.course = '/courses';
  }

  getAll(page: number, size: number): Observable<IPageResponse<ICourse>> {
    return this.HttpClient.get<IPageResponse<ICourse>>(
      `${enviroment.baseUrl}/courses?page=${page}&size=${size}`
    );
  }
  update(course: ICourse): Observable<IGenericResponse<number>> {
    return this.HttpClient.put<IGenericResponse<number>>(
      `${enviroment.baseUrl}/courses`,
      course
    );
  }

  create(course: ICourse): Observable<IGenericResponse<number>> {
    return this.HttpClient.post<IGenericResponse<number>>(
      `${enviroment.baseUrl}/courses`,
      course
    );
  }
  getById(id: number): Observable<IGenericResponse<ICourse>> {
    return this.HttpClient.get<IGenericResponse<ICourse>>(
      `${enviroment.baseUrl}/courses/${id}`
    );
  }
  getByTitle(title: string): Observable<IGenericResponse<ICourse[]>> {
    console.log(`${enviroment.baseUrl + this.course}` + `/search/${title}`);
    return this.HttpClient.get<IGenericResponse<ICourse[]>>(
      enviroment.baseUrl + this.course + `/search/${title}`
    );
  }
  delete(id: number): Observable<IGenericResponse<boolean>> {
    return this.HttpClient.delete<IGenericResponse<boolean>>(
      `${enviroment.baseUrl}/courses/` + id
    );
  }
}
