import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {Observable, of} from "rxjs";
import {AppState} from "./interfaces/app-state";
import {CustomResponse} from "./interfaces/custom-response";
import {catchError, map, startWith} from "rxjs/operators";
import {DataState} from "./enum/data-state.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public appState$?: Observable<AppState<CustomResponse>>;

  constructor(private service: ServerService) {  }

  ngOnInit(): void {
      this.appState$ = this.service.getAllServers()
          .pipe(
              map(response => {
                  return {
                    dataState: DataState.LOADED,
                    appData: response
                  }
              }),

              startWith({
                  dataState: DataState.LOADING,
              }),

              catchError((err: string) => {
                return of<AppState<CustomResponse>>({
                    dataState: DataState.ERROR,
                    error: err
                })
              })
          )
  }


}
