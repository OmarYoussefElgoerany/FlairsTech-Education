import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  constructor(private http: HttpClient) {}
  // .get('http://localhost:8080/api/v1/courses/102', {

  sendRequest() {
    this.http
      .get('http://localhost:8080/api/v1/test/hello', { withCredentials: true })
      .subscribe((response) => {
        console.log(response);
        console.log('IT WORKSSSS');
      });
  }
}
