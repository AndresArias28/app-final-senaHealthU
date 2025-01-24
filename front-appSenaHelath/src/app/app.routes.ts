import { Routes } from '@angular/router';
import { DashboardComponent } from './feature/pages/dashboard/dashboard.component';
import { LoginComponent } from './feature/auth/login/login.component';

export const routes: Routes = [

    {path: '', redirectTo: 'inicio', pathMatch: 'full'},
    {path: 'inicio', component: DashboardComponent,},
    {path: 'iniciar-sesion', component: LoginComponent},
    {path: '**', redirectTo: 'inicio' },
];
