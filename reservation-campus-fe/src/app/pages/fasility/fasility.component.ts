import { Component } from '@angular/core';
import { TemplateAdminComponent } from '../../shared/template-admin/template-admin.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-fasility',
  standalone: true,
  imports: [TemplateAdminComponent, CommonModule],
  templateUrl: './fasility.component.html',
  styleUrl: './fasility.component.scss'
})
export class FasilityComponent {
  facilitiesData: any;

  constructor(private facilityService: FacilitiesService){
    this.facilityService.getFacilities().subscribe({
      next: (data) => this.facilitiesData = data,
      error: (err) => console.error(err)
    })
  }

  ngOnInit(): void {
     
  }

  getFacilitiesData(){
    
  }
}
