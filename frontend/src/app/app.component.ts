import {Component, OnInit} from '@angular/core';
import {ServerService} from "./services/server.service";
import {Observable, of} from "rxjs";
import {AppState} from "./interfaces/app-state";
import {CustomResponse} from "./interfaces/custom-response";
import {catchError, map, startWith} from "rxjs/operators";
import {DataState} from "./enum/data-state.enum";
import {Server} from "./interfaces/server";
import {Status} from "./enum/status.enum";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public appState$?: Observable<AppState<CustomResponse>>;

  public testData: Server[] = [
      {
          id: 0,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP
      },
      {
          id: 1,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP
      },
      {
          id: 2,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP,
      },
      {
          id: 3,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP,
      },
      {
          id: 4,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP,
      },
      {
          id: 5,
          idAddress: "12324",
          name: "Name",
          memory: "twfrwfsdfsdf",
          type: "First",
          imageUrl: "asasasa",
          status: Status.SERVER_UP,
      },
  ];

  public dataSource = new MatTableDataSource(this.testData);

  public displayedColumns: string[] = ['id', 'ipAddress', 'name', 'memory', 'type', 'imageUrl', 'status']

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
