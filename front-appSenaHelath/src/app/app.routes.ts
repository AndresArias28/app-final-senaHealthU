import { Routes } from '@angular/router';
import { DashboardComponent } from './feature/pages/dashboard/dashboard.component';
import { LoginComponent } from './feature/auth/login/login.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
    {path: '', redirectTo: 'iniciar-sesion', pathMatch: 'full'},
    {path: 'inicio-admin', component: DashboardComponent, canMatch: [authGuard]},
    {path: 'iniciar-sesion', component: LoginComponent},
    {path: '**', redirectTo: 'iniciar-sesion' },
];
