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
        path: 'inicio-super', //dashboard para superusuario
        component: DashboardSuperusuarioComponent,
        canActivate: [authGuard, roleGuard],
        data: {role: 'ROLE_Superusuario'}//agregar el rol requerido
    },
    {path: '**', redirectTo: 'iniciar-sesion' },
];
