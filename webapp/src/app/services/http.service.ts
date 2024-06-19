import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  baseUrl = 'http://localhost:8080'

  constructor(private http: HttpClient) {
  }

  getData<T>(endpoint: string) {
    return this.http.get<T>(this.baseUrl + endpoint);
  }

  postData<T>(endpoint: string, body: any) {
    this.http.post<T>(this.baseUrl + endpoint, body)
      .subscribe(response => {
        // Handle successful response with the 'response'
      }, error => {
        // Handle error scenario
      });
  }
}
