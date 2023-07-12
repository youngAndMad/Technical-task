import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private api: string = 'http://localhost:8080/api/v1/qr'

  constructor(private http: HttpClient) {
  }

  getQr(): Observable<any> {
    return this.http.get(this.api, {responseType: 'blob'});
  }

}
