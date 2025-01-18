import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../services/auth/user';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-personal-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './personal-details.component.html',
  styleUrl: './personal-details.component.css'
})
export class PersonalDetailsComponent {
  
  errorMessage: String = "";
  user?:User;

  //llamar al servicio para obtener el usuario del api, los servicios se deben inyectar en el constructor
  constructor(private userService:UserService) { 

    this.userService.getUser(11).subscribe({
      next:(userData)=>{
        this.user=userData;
      },
      error:(error)=>{
        this.errorMessage=error;
      },
      complete:()=>{
        console.info('Completado');
      }
    });
  }
}
