import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { LoginRequest } from './loginRequest';
import {
  BehaviorSubject,
  catchError,
  map,
  Observable,
  tap,
  throwError,
} from 'rxjs';
import { User } from './user';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root', //aseguro que el servicio este disponibleen toda la apliacion
})

export class LoginService {

  //Libreria rxjs para el manejo de observables- BehaviorSubject es un tipo de observable que permite emitir valores a los suscriptores
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  currentUserData: BehaviorSubject<String> = new BehaviorSubject<String>("");

  constructor(private http: HttpClient) {
    this.currentUserLoginOn = new BehaviorSubject<boolean>(sessionStorage.getItem('token') !== null);
    this.currentUserData = new BehaviorSubject<String>(sessionStorage.getItem('token') || '');
  } 

  login(credentials: LoginRequest): Observable<any> {

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

  logout() {
    sessionStorage.removeItem('token');
    this.currentUserLoginOn.next(false);
  }

    
    // return this.http.get<User[]>('assets/data.json').pipe(
    //   map((users: User[]) => {
    //     // Filtra el usuario según las credenciales
    //    const user = users.find((u) => u.emailUsuario === credentials.email);

    //     if (!user) {
    //       throw new Error('Usuario no encontrado o credenciales incorrectas.');
    //     }

    //     // Guardar información en localStorage
    //     localStorage.setItem('currentUser', JSON.stringify(user));

    //     return user;
    //   }),
    //   tap((userData: User) => {
    //     // Actualiza el estado del usuario
    //     this.currentUserData.next(userData);
    //     this.currentUserLoginOn.next(true);
    //   }),
    //   catchError(this.handleError)
    // );


  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producio un error ', error.message);
    } else {
      console.error(`Error del cliente (${error.status}):`, error.message);
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
}
