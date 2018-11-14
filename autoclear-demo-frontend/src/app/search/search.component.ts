import {Component, Input, OnInit, Output} from '@angular/core';
import {IResult, ISearchObject, SearchService} from './search.service';
import {Router} from '@angular/router';
import {OverrideService} from '../override/override.service';
import {IAddtrack} from '../addtrack/addtrack.service';
import {any} from 'codelyzer/util/function';
import { Papa } from 'ngx-papaparse';


const data: {[id: string]: IResult; } = {
    'GBBKS0700574': {
        cleared: 'Clear',
        clearedCssClass: 'cleared-yes',
        recordLabel: 'XL Recordings Limited',
        tuneCode: '8531366C',
        publisher: 'UNIVERSAL MUSIC PUBLISHING LIMITED / KOBALT MUSIC PUBLISHING LTD',
        composer: 'Adkins/White',
        track: 'Chasing Pavements',
        artist: 'Adele',
        isrcNumber: 'GBBKS0700574',
        success: true,
        override: false,
        id: null,
        manual: false
    },
    'GBHMU1700053': {
        cleared: 'Speak to music contact',
        clearedCssClass: 'cleared-check',
        recordLabel: 'Sony Music Entertainment UK Ltd',
        tuneCode: '267848GW',
        publisher: 'Kobalt Music Publishing / Copyright Control',
        composer: 'Andino/Oneill Luis Angel/Perez Soto/Rivera',
        track: 'Reggaetón Lento (Remix)',
        artist: 'CNCO & LITTLE MIX',
        isrcNumber: 'GBHMU1700053',
        success: true,
        override: false,
        id: null,
        manual: false
    },
    'USBWC0110047': {
        cleared: 'Not cleared',
        clearedCssClass: 'cleared-no',
        recordLabel: 'Demon Music Group Ltd',
        tuneCode: '002724CN',
        publisher: 'WARNER/CHAPPELL NORTH AMERICA LIMITED/EMI MUSIC PUBLISHING LTD/SONY/ATV MUSIC PUBLISHING (UK) LIMITED',
        composer: 'Jackson/Miner/Smith',
        track: 'Higher and Higer',
        artist: 'Jackie Wilson',
        isrcNumber: 'USBWC0110047',
        success: true,
        override: false, 
        id: null,
        manual: false
    }
};


@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
    isrcNumber: string;
    recordLabel: string;
    error: boolean;
    choices: IResult[] = [];
    dataSource: IResult[] = [];
    displayedColumns: string[] = ['artist', 'trackTitle', 'clear', 'composer', 'publisher', 'tuneCode', 'label', 'isrcNumber', 'copyData'];
    spinning: boolean = false;
    
    public constructor(private searchService: SearchService, private router: Router, private overrideService: OverrideService, private papa: Papa) { }

    ngOnInit() {
        this.reset();
        this.choices = [];
        
        for (let key in data) {
            let value: IResult = data[key];
            this.choices.push(value);
        }
    }
    
    private init(isrc: string, label: string){
        this.ngOnInit();
        this.isrcNumber = isrc;
        this.recordLabel = label;
        
        this.search();
    }

    public searchInputsDisabled(): boolean {
        return !!this.dataSource.length;
    }

    public searchDisabled(): boolean {
        if (this.dataSource.length > 0){
            return true;
        } else if (this.isrcNumber.length < 6){
            return true;
        } else if (this.recordLabel.length < 6){
            return true;
        }
        return false;
    }
    
    public reset(): void {
        this.isrcNumber = '';
        this.recordLabel = '';
        this.error = false;
        this.dataSource = [];

    }
    
    public search(): void {
        const s: ISearchObject = {
            isrcNumber: this.isrcNumber,
            recordLabel: this.recordLabel
        };

        this.searchService.getClearance(s).subscribe((data: IResult) => {
            if (data.success){
                this.dataSource = [data];
                this.error = false;
            } else {
                this.dataSource = [];
                this.error = true;
            }

        });
        
        // if (data[this.isrcNumber] && data[this.isrcNumber].recordLabel === this.recordLabel) {
        //     this.dataSource = [data[this.isrcNumber]];
        //     this.error = false;
        // } else {
        //     this.dataSource = [];
        //     this.error = true;
        // }
    }
    
    public choiceClicked(choice: IResult): void {
        this.dataSource = [];
        this.isrcNumber = choice.isrcNumber;
        this.recordLabel = choice.recordLabel;
    }
    
    public override(): void {
        this.router.navigateByUrl(`/override/${this.dataSource[0].tuneCode}`);
    }

    public deleteOverride(): void {
        this.overrideService.delete(this.dataSource[0].tuneCode).subscribe((data: void) => {
            this.init(this.dataSource[0].isrcNumber, this.dataSource[0].recordLabel);
        });
    }
    
    public addTrack(): void {
        this.router.navigateByUrl('/add/', { queryParams: { isrcNumber: this.isrcNumber, recordLabel: this.recordLabel } });
    }
    
    public editManual(): void {
        this.router.navigateByUrl(`/add/${this.dataSource[0].tuneCode}`);
    }
    
    public searchBulkDisabled(): boolean{
        return false;
    }

    public fileChanged(files: FileList): void {
        this.dataSource = [];
        if(files && files.length > 0) {
            let file : File = files.item(0);
            let reader: FileReader = new FileReader();
            reader.readAsText(file);
            reader.onload = (e) => {
                const csvData: string = reader.result.toString();
                const options = {
                    complete: (results) => {
                        const newDataSource: IResult[] = [];
                        results.data.forEach((row: string) => {
                            const isrcNumber: string = row[0];
                            const recordLabel: string = row[1];
                            if (isrcNumber && recordLabel){

                                newDataSource.push({
                                    isrcNumber: isrcNumber,
                                    recordLabel: recordLabel
                                });
                            }
                        });
                        
                        this.dataSource = newDataSource;
                    }
                    // Add your options here
                };

                this.papa.parse(csvData,options);
            }
        }
    }
    
    public searchBulk(): void {
        const s: ISearchObject[] = [];
        
        this.dataSource.forEach((row: IResult) => {
            s.push({
                isrcNumber: row.isrcNumber,
                recordLabel: row.recordLabel
            });
        });

        this.searchService.getBulkClearance(s).subscribe((data: number) => {
            this.getBulkResults(data);
        });
    }
    
    private getBulkResults(i: number): void {
        this.spinning = true;
        setTimeout(() => {
            this.searchService.getBulkClearanceResults(i).subscribe((data: IResult[]) => {
                if (data.length){
                    this.dataSource = data;
                    this.spinning = false;
                } else {
                    this.getBulkResults(i);
                }
                
            });
        }, 5000);
    }
}
