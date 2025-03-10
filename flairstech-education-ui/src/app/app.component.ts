import { Component, ElementRef, ViewChild } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer.component';
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  imports: [NavbarComponent, FooterComponent, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'flairstech-education-ui';
  showAlert: boolean = false;
  event$!: Event;

  onSubmit(event$: Event) {
    this.showAlert = true;
    event$.preventDefault();
    // Hide alert after 3 seconds
    setTimeout(() => {
      this.showAlert = false;
    }, 3000);
  }
}
