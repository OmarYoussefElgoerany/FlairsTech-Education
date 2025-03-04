import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  isLoggedIn!: boolean;
  constructor(private auth: AuthService) {
    this.isLoggedIn = false;
  }
  ngOnInit(): void {
    this.auth.userLoggedIn$.subscribe({
      next: (val) => {
        this.isLoggedIn = val;
      },
    });
  }
  logOut() {
    this.auth.logOut();
  }
}
