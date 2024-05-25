import { Component } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { TemplateAdminComponent } from '../../shared/template-admin/template-admin.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard-admin',
  standalone: true,
  imports: [TemplateAdminComponent, RouterOutlet],
  templateUrl: './dashboard-admin.component.html',
  styleUrl: './dashboard-admin.component.scss'
})
export class DashboardAdminComponent {
 

  constructor(){
    let token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJXRUIgUkVTRVJWQVRJT04gQ0FNUFVTIiwic3ViIjoiNzk1NzdlZGUtZDI5MC00YWRlLWExZDEtNmFmNzAxMWRlNGU4IiwiZXhwIjoxNzE1Njk5MzkyLCJpYXQiOjE3MTU2OTU3OTIsInJvbGUiOiJST0xFX0FETUlOIn0.kdyJhrm3mbrvK7QOtbLBWNNm-V4jWppuHaZYY4Px3PU"
    const jwt = jwtDecode(token);
    
    console.log(new Date(1715699392 * 1000))
    console.log(jwt);

  }
  
 
}
