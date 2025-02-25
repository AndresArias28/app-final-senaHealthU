import { CommonModule } from '@angular/common';
import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login/login.service';
import { LoginRequest } from '../../../shared/models/loginRequest';
import { jwtDecode } from 'jwt-decode';
import { toast } from 'ngx-sonner';
declare var window: any;

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {

  mostrarModal() {
    const modalElement = document.getElementById('modalRecuperar');
    if (modalElement) {
      const modal = new window.bootstrap.Modal(modalElement);
      modal.show();
    }
  }

  loginError: string = '';
  loginForm;
  message: string = '';
  showPassword = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router, 
    private loginService: LoginService,
    private ngZone: NgZone 
  ) {
    //validaciones del formulario reactivo
    this.loginForm = this.formBuilder.group({
      emailUsuario: ['', [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$') ]],
      contrasenaUsuario: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  login() {  //metodo del boton iniciar sesion
    //formularios reactivos
    if (this.loginForm.valid) {//validar el formulario
      this.loginService.login(this.loginForm.value as LoginRequest).subscribe({//
        next: (data) => {

          const rol = this.loginService.getRole();
          console.log('Rol obtenido:', rol);

          // Redirigir según el rol
          if (rol == 'ROLE_Administrador') {
            console.log("deberai entrar aca");
              this.router.navigate(['/inicio-admin']);
          } else if (rol === 'ROLE_Superusuario') {
              this.router.navigate(['/inicio-super']);
          } else {
            toast.error('rol de usuario no autorizado');
            this.router.navigate(['/iniciar-sesion']);
          } 
       
        },
        error: (error) => {
          console.log(error.message);
          this.loginError = error.message;//almacenar en loginError el mensaje de error que es mostrado en el html

        },
        complete: () => {
          console.log('complete');
          this.loginForm.reset();
        }
      })

    } else {
      this.loginForm.markAllAsTouched();
      this.loginError = 'Error en el formulario';
    }
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  get email() {
    return this.loginForm.controls.emailUsuario;
  }

  get password() {
    return this.loginForm.controls.contrasenaUsuario;
  }

}
