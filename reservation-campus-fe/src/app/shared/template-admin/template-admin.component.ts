import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-template-admin',
  standalone: true,
  imports: [NgClass, RouterLink, RouterOutlet, RouterLinkActive],
  templateUrl: './template-admin.component.html',
  styleUrl: './template-admin.component.scss'
})
export class TemplateAdminComponent {
  isOpen:boolean = false;
  toogle(){
    this.isOpen = !this.isOpen;
  }
}
