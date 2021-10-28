import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {CustomResponse} from "../interfaces/custom-response";
import {catchError, tap} from "rxjs/operators";
import {Server} from "../interfaces/server";

@Injectable({ providedIn: 'root' })
export class ServerService {

    private static readonly ALL_SERVERS_URL: string = 'http://localhost:8080/server/list'
    private static readonly SAVE_SERVER_URL: string = 'http://localhost:8080/server/save'
    private static readonly PING_SERVER_URL: string = 'http://localhost:8080/server/ping'
    private static readonly DELETE_SERVER_URL: string = 'http://localhost:8080/server/delete/name'

    private readonly PAGE_NUMBER: number = 1
    private readonly PAGE_SIZE: number = 20

    constructor(private http: HttpClient) {  }

    public getAllServers(): Observable<CustomResponse> {
        const observable = this.http.get<CustomResponse>(ServerService.ALL_SERVERS_URL, {
            params: {
                page_count: this.PAGE_SIZE,
                page_number: this.PAGE_NUMBER
            }
        });
        observable.pipe(
            tap(console.log),
            catchError(ServerService.handleError)
        );
        return observable;
    }

    public saveServer(server: Server): Observable<CustomResponse> {
        const observable = this.http.post<CustomResponse>(ServerService.SAVE_SERVER_URL, server);
        observable.pipe(
            tap(console.log),
            catchError(ServerService.handleError)
        );
        return observable;
    }

    public pingServer(apiAddress: string): Observable<CustomResponse> {
        const observable = this.http.get<CustomResponse>(ServerService.PING_SERVER_URL, {
            params: {
                server_address: apiAddress
            }
        });
        observable.pipe(
            tap(console.log),
            catchError(ServerService.handleError)
        );
        return observable
    }

    public deleteServer(serverName: string): Observable<CustomResponse> {
        const observable = this.http.get<CustomResponse>(ServerService.DELETE_SERVER_URL, {
            params: {
                name: serverName
            }
        })
        observable.pipe(
            tap(console.log),
            catchError(ServerService.handleError)
        )
        return observable
    }

    private static handleError(error: HttpErrorResponse): Observable<never> {
        console.log(error)

        return throwError('An error occurred - Error code' + error.status)
    }
}