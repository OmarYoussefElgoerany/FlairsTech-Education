import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = inject(AuthService).getAuthToken();

  if (authToken) {
    // Check if token exists
    console.log('DONEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE');
    const newReq = req.clone({
      setHeaders: { Authorization: `Bearer ${authToken}` },
    });
    return next(newReq);
  }

  return next(req);
};
