import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {Observable, of} from "rxjs";
import {AppState} from "./interfaces/app-state";
import {CustomResponse} from "./interfaces/custom-response";
import {catchError, map, startWith} from "rxjs/operators";
import {DataState} from "./enum/data-state.enum";
import {Server} from "./interfaces/server";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public appState$?: Observable<AppState<CustomResponse>>;

  public showLoader: boolean = true;

  public dataSource: Server[] = []

  public displayedColumns: string[] = ['id', 'ipAddress', 'name', 'memory', 'type', 'imageUrl', 'status', 'actions'];

  constructor(private service: ServerService) {  }

  ngOnInit(): void {
      this.service.getAllServers()
          .pipe(
              map(response => {
                  console.log(response)
                  this.showLoader = false
                  this.dataSource = response?.data as Server[] || []
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
          .subscribe()
  }

  onPingServerClicked(serverId: number) {
      console.log("Called ping server with: " + serverId)
  }

  onEditServerClicked(serverId: number) {
      console.log("Called edit server with: " + serverId)
  }

  onDeleteServerClicked(serverId: number) {
      console.log("Called delete server with: " + serverId)
  }
}
