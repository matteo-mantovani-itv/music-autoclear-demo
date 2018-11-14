import {Component, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from './login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    username: string;
  
    constructor(private router: Router, private loginservice: LoginService) { }

    ngOnInit() {
        
    }

    login(): void {
        this.loginservice.setUsername(this.username);
        this.router.navigateByUrl('/');
    }
}
