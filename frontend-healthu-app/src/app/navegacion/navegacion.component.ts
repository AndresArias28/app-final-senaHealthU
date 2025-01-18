import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LoginService } from '../services/auth/login.service';


@Component({
  selector: 'app-navegacion',
  templateUrl: './navegacion.component.html',
  styleUrl: './navegacion.component.css',
  standalone: true,
  imports: [
     CommonModule,
     RouterOutlet,
     RouterLink,
  ]
})
export class NavegacionComponent implements OnInit {

  userLoggedIn: boolean = false;

  constructor(private ls:LoginService) { }

  // ngOnDestroy(): void {
  //   this.ls.currentUserLoginOn.unsubscribe();
  // }

  ngOnInit(): void {
    this.ls.currentUserLoginOn.subscribe(
      {
        next: (userLoggedIn) => {
          this.userLoggedIn = userLoggedIn;
        }
      }
    )
  }
}
