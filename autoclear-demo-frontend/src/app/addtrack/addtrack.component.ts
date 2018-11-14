import { Component, OnInit } from '@angular/core';
import {AddtrackService, IAddtrack} from './addtrack.service';
import {IResult, SearchService} from '../search/search.service';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-addtrack',
  templateUrl: './addtrack.component.html',
  styleUrls: ['./addtrack.component.scss']
})
export class AddtrackComponent implements OnInit {
    public track = {};
    private dummyTrack: IAddtrack = {
        id: 1,
        isrcNumber: '1',
        recordLabel: '1',
        cleared: '1',
        tuneCode: '1',
        publisher: '1',
        composer: '1',
        track: '1',
        artist: '1'
    };
    public saved: boolean;
    public clearedOptions : string[] = [
        'Clear',
        'Not cleared',
        'Speak to music contact'
    ];
    
    constructor(private addtrackService: AddtrackService, private router: Router, private searchService: SearchService, private route: ActivatedRoute) { }

    ngOnInit() {
        const tuneCode: string = this.route.snapshot.paramMap.get('tuneCode');

        this.searchService.getClearanceByTuneCode(tuneCode).subscribe((data: IResult) => {
            if (data.success){
                this.track = data;
                Object.keys(this.track).forEach(key => {
                    if (!this.dummyTrack[key]){
                        this.track[key] = undefined;
                    }
                });
            }
        });
    }

    save(): void {
        
        this.addtrackService.save(this.track).subscribe((data: number) => {
            this.saved = true;
        });
    }

    canSave(): boolean {
        return true;
    }
    
    cancel(): void {
        this.router.navigateByUrl('');
    }
    
}
