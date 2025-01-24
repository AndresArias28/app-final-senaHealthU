import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {

  constructor() { }

  //interceptar la peticion http y manejar los errores
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    //
    return next.handle(req).pipe( //pasar la peticion al siguiente manejador(API)//pipe es un operador que permite encadenar operaciones
      catchError(error => {//manejar los errores
        console.log(error);
        return throwError(() => error);
      })
    );
  }
}
