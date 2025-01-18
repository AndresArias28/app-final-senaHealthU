import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavegacionComponent } from "../../navegacion/navegacion.component";
import { User } from '../../services/auth/user';
import { LoginService } from '../../services/auth/login.service';
import { PersonalDetailsComponent } from '../../components/personal-details/personal-details.component';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavegacionComponent, PersonalDetailsComponent  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})

export class DashboardComponent implements OnInit , OnDestroy {

  userLoginOn:boolean=false;
  userData?:User;
  
  constructor(private loginService:LoginService) { }

  ngOnDestroy(): void {

    // this.loginService.currentUserData.unsubscribe();
    // this.loginService.currentUserLoginOn.unsubscribe();
  }

  ngOnInit(): void {

    this.loginService.currentUserLoginOn.subscribe({
      next:(userLoginOn) => {
        this.userLoginOn=userLoginOn;
      }
    });
 
    // this.loginService.currentUserData.subscribe({
    //   next:(userData )=>{
    //     this.userData =userData as User;
    //   }
    // });
  }
}
