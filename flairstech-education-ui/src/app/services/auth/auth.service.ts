import { enviroment } from './../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { IRegisterRequest } from '../../models/authModels/IRegisterRequest';
import { IAuthResponse } from '../../common/IAuthResponse';
import { ILoginRequest } from '../../models/authModels/ILoginRequest';
import { IGenericResponse } from '../../common/IGenericResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private userLoggedIn: BehaviorSubject<boolean>;
  userLoggedIn$: Observable<boolean>;

  constructor(private HttpClient: HttpClient) {
    this.userLoggedIn = new BehaviorSubject<boolean>(false);
    this.userLoggedIn$ = this.userLoggedIn.asObservable();
  }

  setAuthToken(token: string): void {
    localStorage.setItem('authToken', token); // Ensure consistent key
  }

  getAuthToken(): string {
    return localStorage.getItem('authToken') ?? ''; // Use the same key
  }
  logOut() {
    localStorage.removeItem('authToken');
    this.userLoggedIn.next(false);

    // Use the same key
  }
  register(
    registerReq: IRegisterRequest
  ): Observable<IGenericResponse<string>> {
    return this.HttpClient.post<IGenericResponse<string>>(
      `${enviroment.baseUrl}` + `/auth/register`,
      registerReq
    );
  }
  login(loginReq: ILoginRequest): Observable<IAuthResponse> {
    return this.HttpClient.post<IAuthResponse>(
      `${enviroment.baseUrl}/auth/authenticate`,
      loginReq
    ).pipe(tap(() => this.userLoggedIn.next(true)));
  }
}
