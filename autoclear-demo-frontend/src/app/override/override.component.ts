import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {IResult, ISearchObject, SearchService} from '../search/search.service';
import {IOverride, OverrideService} from './override.service';

@Component({
  selector: 'app-override',
  templateUrl: './override.component.html',
  styleUrls: ['./override.component.scss']
})
export class OverrideComponent implements OnInit {
  public result: IResult;
  public resultCopy: IResult;
  public error: boolean;
  public saved: boolean;
  
  public clearedOptions : string[] = [
      'Clear',
      'Not cleared',
      'Speak to music contact'
  ];
    
  constructor(private route: ActivatedRoute, private searchService: SearchService, private router: Router, private overrideService: OverrideService) { }

  ngOnInit() {
      const tuneCode: string = this.route.snapshot.paramMap.get('tuneCode');

      this.searchService.getClearanceByTuneCode(tuneCode).subscribe((data: IResult) => {
          if (data.success){
              this.result = data;
              this.resultCopy = { ... data};
              this.error = false;
          } else {
              this.error = true;
          }

      });
  }
  
  public save(): void {
      const s : IOverride = {
          tuneCode: this.resultCopy.tuneCode,
          cleared: this.resultCopy.cleared
      };

      this.overrideService.save(s).subscribe((data: number) => {
          this.ngOnInit();
          this.saved = true;
      });
  }
  
  public isChanged(): boolean {
      if (this.result.cleared === this.resultCopy.cleared){
          return false;
      } else {
          return true;
      }
  }
  
  public cancel(): void {
      this.router.navigateByUrl('');
  }

}
