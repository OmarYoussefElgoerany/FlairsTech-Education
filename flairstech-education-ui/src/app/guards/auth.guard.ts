import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService); // Inject the AuthService
  const router = inject(Router); // Inject the Router
  let isLoggedIn: boolean = false;
  authService.userLoggedIn$.subscribe({
    next: (state) => (isLoggedIn = state),
  });
  if (isLoggedIn) {
    return true; // Allow access to the route
  } else {
    // Redirect to the login page with the return URL
    router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false; // Deny access to the route
  }
};
