import { CanMatchFn } from '@angular/router';
import { LoginService } from '../services/login/login.service';
import { inject } from '@angular/core';

export const authGuard: CanMatchFn = (route, segments) => {
  const loginService = inject(LoginService);
  if (loginService.userToken) {
    console.log('No hay usuario autenticado Redirigiendo a la página de inicio de sesión');
    return true;
  }
  return false;



};
