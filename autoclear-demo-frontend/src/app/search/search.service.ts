import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {IAddtrack} from '../addtrack/addtrack.service';


export interface IResult extends IAddtrack {
    clearedCssClass?: string;
    success?: boolean;
    override?: boolean;
    manual?: boolean;
}

export interface ISearchObject {
    isrcNumber: string;
    recordLabel: string;
}

export interface ISearchService {
    getClearance(s: ISearchObject): Observable<IResult>;
    getClearanceByTuneCode(tuneCode: string): Observable<IResult>;
}

@Injectable({
    providedIn: 'root',
})
export class SearchService implements ISearchService {
    public constructor(private http: HttpClient) {
        
    };

    getClearance(s: ISearchObject): Observable<IResult> {
        const url = `http://127.0.0.1:9200/?isrcNumber=${s.isrcNumber}&recordLabel=${s.recordLabel}`;
        return this.http.get<IResult>(url);
    }

    getClearanceByTuneCode(tuneCode: string): Observable<IResult> {
        const url = `http://127.0.0.1:9200/?tuneCode=${tuneCode}`;
        return this.http.get<IResult>(url);
    }

    getBulkClearance(s: ISearchObject[]): Observable<number> {
        const url = `http://127.0.0.1:9200/bulkSearch`;
        return this.http.post<number>(url, s);
    }

    getBulkClearanceResults(databaseId: number): Observable<IResult[]> {
        const url = `http://127.0.0.1:9200/bulkSearch?databaseId=${databaseId}`;
        return this.http.get<IResult[]>(url);
    }
}

