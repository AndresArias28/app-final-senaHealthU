import { Routes } from '@angular/router';
import { DashboardComponent } from './feature/pages/dashboard/dashboard.component';
import { LoginComponent } from './feature/auth/login/login.component';
import { authGuard } from './core/guards/auth.guard';
import { DashboardSuperusuarioComponent } from './feature/pages/dashboard-superusuario/dashboard-superusuario.component';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [
    {path: '', redirectTo: 'iniciar-sesion', pathMatch: 'full'},
    
    {
        path: 'inicio-admin',
        component: DashboardComponent, 
        //canActivate: [authGuard, roleGuard],
        data: {role: 'ROLE_Administrador'}
    },
    {
        path: 'iniciar-sesion', 
        component: LoginComponent
    },
    {
        path: 'inicio-super', 
        component: DashboardSuperusuarioComponent,
        canActivate: [authGuard, roleGuard],
        data: {role: 'ROLE_Superusuario'}
    },
    {path: '**', redirectTo: 'iniciar-sesion' },
];
