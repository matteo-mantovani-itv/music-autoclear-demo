import { Injectable } from '@angular/core';

export interface ILoginService {
    isLoggedIn(): boolean;
    
    getUsername(): string;

    setUsername(username: string): void;
    
    logOut(): void;
}

@Injectable({
    providedIn: 'root',
})
export class LoginService implements ILoginService {
    loggedIn: boolean = false;
    username: string = null;
    
    setUsername(username) {
        this.username = username;
        this.loggedIn = true;
    }
    
    getUsername(){
        return this.username;
    }
    
    isLoggedIn(){
        return this.loggedIn;
    }
    
    logOut(){
        this.username = null;
        this.loggedIn = false;
    }
}

