import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../../core/services/login/login.service';
import { LoginRequest } from '../../../shared/models/loginRequest';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {

  loginError: string = '';
  loginForm;

  constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) {
    //validaciones del formulario reactivo
    this.loginForm = this.formBuilder.group({
      emailUsuario: ['', [Validators.required, Validators.email, ]],
      contrasenaUsuario: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit(): void {}

  login() {  //metodo del boton iniciar sesion
    //formularios reactivos
    if (this.loginForm.valid) {//validar el formulario
      this.loginService.login(this.loginForm.value as LoginRequest).subscribe({//
        next: (data) => {
          //console.log(data);
        },
        error: (error) => {
          console.log(error.message);
          this.loginError = error.message;//almacenar en loginError el mensaje de error que es mostrado en el html

        },
        complete: () => {
          console.log('complete');
          this.router.navigate(['/inicio-admin']);
          this.loginForm.reset();
        }
      })

    } else {
      this.loginForm.markAllAsTouched();
      this.loginError = 'Error en el formulario';
    }
  }

  get email() {
    return this.loginForm.controls.emailUsuario;
  }

  get password() {
    return this.loginForm.controls.contrasenaUsuario;
  }

}
