import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


export interface IOverride {
    tuneCode: string;
    cleared: string;
}

export interface IOverrideService {
    save(s: IOverride): Observable<number>;
}

@Injectable({
    providedIn: 'root',
})
export class OverrideService implements IOverrideService {
    public constructor(private http: HttpClient) {};

    save(s: IOverride): Observable<number> {
        const url = `http://127.0.0.1:9200/override/${s.tuneCode}`;
        return this.http.post<number>(url, s);
    }
    
    delete(tuneCode: string): Observable<void> {
        const url = `http://127.0.0.1:9200/override/${tuneCode}`;
        return this.http.delete<void>(url);
    }
}


