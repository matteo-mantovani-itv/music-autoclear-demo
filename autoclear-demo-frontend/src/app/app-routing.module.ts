import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {SearchComponent} from './search/search.component';
import {OverrideComponent} from './override/override.component';
import {AddtrackComponent} from './addtrack/addtrack.component';

const routes: Routes = [
    { path: 'add/:tuneCode', component: AddtrackComponent},
    { path: 'add/', component: AddtrackComponent},
    { path: 'override/:tuneCode', component: OverrideComponent },
    { path: 'login', component: LoginComponent },
    { path: '', component: SearchComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
