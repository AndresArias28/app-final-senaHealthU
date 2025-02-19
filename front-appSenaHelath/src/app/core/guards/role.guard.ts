import { CanMatchFn } from '@angular/router';
import { LoginService } from '../services/login/login.service';
import { inject } from '@angular/core';

export const roleGuard: CanMatchFn = (route, segments) => {

   const loginService = inject(LoginService);
    //console.log('AuthGuard', loginService.userToken);
    const rolUsuario = loginService.getRole();
    console.log('Rol obtenido:', rolUsuario);
    const rolRequerido = route.data?.['role'];
  
    return rolUsuario === rolRequerido;
};
