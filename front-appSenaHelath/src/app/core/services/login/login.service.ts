import { Injectable } from '@angular/core';
import { LoginRequest } from '../../../shared/models/loginRequest';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, catchError, map, Observable, tap, throwError } from 'rxjs';
import { User } from '../../../shared/models/user';
import { environment } from '../../../../environments/environmets';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  currentUserLoginOn = new BehaviorSubject<boolean>(false);
  currentUserData = new BehaviorSubject<String>("");

  private dataUrl = 'assets/data.json';

  //servicio para el login utilizando httpclient, se inyecta en el constructor 
  // agregar el proveedor en app.config.ts
  constructor(private http: HttpClient) {
    this.currentUserLoginOn = new BehaviorSubject<boolean>(sessionStorage.getItem('token') !== null);
    this.currentUserData = new BehaviorSubject<String>(sessionStorage.getItem('token') || '');
 
  }

  login(credentials: LoginRequest): Observable<any> {
    // return this.http.get<User>('assets/data.json').pipe(
    //   tap((userData : User) => {
    //     //si todo sale bien encadeno una serie de operaciones con tap
    //     sessionStorage.setItem("token", userData);//guarda el token en el localStorage
    //     this.currentUserData.next(userData);//emitir informacion a los componentes suscritos
    //     this.currentUserLoginOn.next(true);
    //   }),
    //   catchError(this.handleError)
    // );

    return this.http.post<any>(environment.urlHost+"auth/login",credentials).pipe(
      tap( (userData) => {//si todo sale bien encadeno una serie de operaciones con tap
        sessionStorage.setItem("token", userData.token);//guarda el token en el localStorage
        this.currentUserData.next(userData.token);
        this.currentUserLoginOn.next(true);
      }),
      map((userData)=> userData.token),//transforma el objeto y devuelve el token
      catchError(this.handleError)
    );
  }

    private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producio un error ', error.error);
    } else {
      console.error('Backend retornó el código de estado ', error);
    }
    return throwError(
      () => new Error('Algo falló. Por favor intente nuevamente.')
    );
  }

  //observable que emite el estado actual del usuario
  get userData(): Observable<String | null> {
    return this.currentUserData.asObservable();
  }

  //observable que emite el estado actual del login
  get userLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

  get userToken(): String  {
    return this.currentUserData.value;
  }

  logout() {
    sessionStorage.removeItem('token');
    this.currentUserLoginOn.next(false);
  }
}
