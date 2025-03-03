import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-recuperar-contrasena',
  imports: [FormsModule],
  templateUrl: './recuperar-contrasena.component.html',
  styleUrl: './recuperar-contrasena.component.css'
})

export class RecuperarContrasenaComponent {

  correoIngresado: string = '';
  message: string = '';


  recoverPassword() {
  throw new Error('Method not implemented.');//todo: metodo para recuperar contraseña
  }
 

  validarTextoIngresado() {
    throw new Error('Method not implemented.');
  }

  @Output() modalAbierto = new EventEmitter<void>();

  abrirModal() {
    this.modalAbierto.emit(); // Envia el evento al padre (LoginComponent)
  }

  recuperarContrasena() {
    if(this.correoIngresado === '') {
      this.message = 'Por favor ingrese un correo'; 
    }
  }

}
