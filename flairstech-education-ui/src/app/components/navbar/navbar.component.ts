import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  isLoggedIn!: boolean;
  constructor(private auth: AuthService) {
    this.isLoggedIn = true;
  }
  logOut() {
    this.auth.logOut();

    this.auth.userLoggedIn$.subscribe({
      next: (val) => {
        this.isLoggedIn = val;
      },
    });
  }
}
