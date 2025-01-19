import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private isBrowser: boolean = false;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  setToken(token: string): void {
    if (this.isBrowser) {
      sessionStorage.setItem('token', token); // Solo se ejecuta en el navegador
    }
  }

  getToken(): string | null {
    if (this.isBrowser) {
      return sessionStorage.getItem('token'); // Solo se ejecuta en el navegador
    }
    return null; // Retorna null en el servidor
  }

  clearToken(): void {
    if (this.isBrowser) {
      sessionStorage.removeItem('token'); // Solo se ejecuta en el navegador
    }
  }

}
