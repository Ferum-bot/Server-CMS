import {Injectable} from "@angular/core";
import {Status} from "../enum/status.enum";
import {CustomResponse} from "../interfaces/custom-response";
import {Observable, Subscriber} from "rxjs";

@Injectable({ providedIn: 'root' })
export class ServersFilterService {

    private static readonly FILTERED_MESSAGE = 'Servers filtered by status: '

    constructor() {  }

    public filterServersByStatus(status: Status, response: CustomResponse): Observable<CustomResponse> {
        return new Observable<CustomResponse>(
            subscriber => {
                this.filterFunction(subscriber, status, response)
            }
        );
    }

    private filterFunction(subscriber: Subscriber<CustomResponse>, status: Status, response: CustomResponse) {
        switch (status) {
            case Status.ALL: {
                response.message = ServersFilterService.FILTERED_MESSAGE + Status.ALL;
                subscriber.next(response);
                break;
            }
            case Status.SERVER_UP: {
                response.message = ServersFilterService.FILTERED_MESSAGE + Status.SERVER_UP;
                response.data?.servers?.filter(server => server.status === Status.SERVER_UP);
                subscriber.next(response)
                break;
            }
            case Status.SERVER_DOWN: {
                response.message = ServersFilterService.FILTERED_MESSAGE + Status.SERVER_DOWN;
                response.data?.servers?.filter(server => server.status === Status.SERVER_DOWN);
                subscriber.next(response)
                break;
            }
        }
        subscriber.complete()
    }
}