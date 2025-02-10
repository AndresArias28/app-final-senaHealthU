import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root'
})

export class JwtInterceptorService  implements HttpInterceptor {

  constructor(private loginService: LoginService) { }

  //interceptar la peticion http y agregar el token en el header
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('Interceptor ejecutado para la solicitud:', req.url);

    const token: String | null = this.loginService.userToken;//acceder al token del usuario
    console.log("Token obtenido desde ek interceptor:", token);
    console.log("Solicitud interceptada:", req);


    if (token != "") {//si el token no esta vacio
      console.log("cloanando la solicitud");
      req = req.clone({//clonar la peticion y agregar el token en el header
        setHeaders: {
          'Content-Type': 'application/json; charset=utf-8',//juego de caracteres para enviar datos
          'Accept': 'application/json',
          'Authorization': `Bearer ${token}` //token de autorizacion
        }
      });
    }else {
      console.warn('No se encontró un token válido para esta solicitud');
    }
    //pasar el token al siguiente manejador(API)
    return next.handle(req);
  }
}
