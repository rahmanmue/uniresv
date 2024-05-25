import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-navbar-user',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
  ],
  templateUrl: './navbar-user.component.html',
  styleUrl: './navbar-user.component.scss'
})
export class NavbarUserComponent {
  constructor(private router: Router, private activeRoute: ActivatedRoute) {
    
  }
}
