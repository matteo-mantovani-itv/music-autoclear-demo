import {Component, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from './login/login.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    constructor(private loginService: LoginService, private router: Router) { }
    
    ngOnInit() {
        // if (!this.loginService.isLoggedIn()){
        //     this.router.navigateByUrl('login');
        // }
    }

    getUsername(): string {
        return this.loginService.getUsername();
    }
    
    isLoggedIn(): boolean {
        return this.loginService.isLoggedIn();
    }
    
    logout(): void {
        this.loginService.logOut();
        this.router.navigateByUrl('login');
    }
    
}
