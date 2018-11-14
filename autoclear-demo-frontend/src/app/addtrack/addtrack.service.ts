import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';


export interface IAddtrack {
    id?: number;
    isrcNumber?: string;
    recordLabel?: string;
    cleared?: string;
    tuneCode?: string;
    publisher?: string;
    composer?: string;
    track?: string;
    artist?: string;
}


export interface IAddtrackService {
    save(s: IAddtrack): Observable<number>;
}

@Injectable({
    providedIn: 'root',
})
export class AddtrackService implements IAddtrackService {
    public constructor(private http: HttpClient){}
    
    save(s: IAddtrack): Observable<number> {
        const url = `http://127.0.0.1:9200/`;
        return this.http.post<number>(url, s);
    }
}

