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
  deleteData<T>(endpoint: string) {
    return this.http.delete<T>(this.baseUrl + endpoint);
  }
  postData<T>(endpoint: string, body: any) {
    return this.http.post<T>(this.baseUrl + endpoint, body)
  }
  editData<T>(endpoint: string, body: any) {
    return this.http.put<T>(this.baseUrl + endpoint, body)
  }
}
