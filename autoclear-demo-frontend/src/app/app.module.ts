import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { SearchComponent } from './search/search.component';
import {FormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {HttpClientModule} from '@angular/common/http';
import { OverrideComponent } from './override/override.component';
import { AddtrackComponent } from './addtrack/addtrack.component';
import { PapaParseModule } from 'ngx-papaparse';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchComponent,
    OverrideComponent,
    AddtrackComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      NoopAnimationsModule,
      FormsModule,
      MatTableModule,
      HttpClientModule,
      PapaParseModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
