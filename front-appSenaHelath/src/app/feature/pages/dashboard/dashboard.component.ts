import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavComponent } from '../../../shared/nav/nav.component';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../core/services/login/login.service';
import { User } from '../../../shared/models/user';
import { PersonalDetailsComponent } from "../personal-details/personal-details.component";

@Component({
  selector: 'app-dashboard',
  imports: [NavComponent, CommonModule, PersonalDetailsComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  userLoginOn: boolean = false;
  userData?: User;

  constructor(private loginService: LoginService) {}
  // ngOnDestroy(): void {
  //   this.loginService.currentUserLoginOn.unsubscribe();
  // }

  ngOnInit(): void {

    this.loginService.currentUserLoginOn.subscribe({
      next: (userLoginOn) => {
        this.userLoginOn = userLoginOn; //almacena el estado del login en la variable userLoggedIn
      },
    });

    // this.loginService.currentUserData.subscribe({
    //   next: (userData) => {
    //     console.log(userData);
    //     this.userData = userData as User;
    //   },
    // });

  }
}
