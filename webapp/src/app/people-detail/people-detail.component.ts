import {Component, inject, OnInit} from '@angular/core';
import {People} from "../models/people.model";
import {HttpService} from "../services/http.service";
import {Observable} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-people-detail',
  standalone: true,
  imports: [
    AsyncPipe
  ],
  templateUrl: './people-detail.component.html',
})
export class PeopleDetailComponent implements OnInit {

  httpService = inject(HttpService)
  route = inject(ActivatedRoute)

  detailData$ : Observable<People> | undefined
  peopleId : string | undefined | null

  ngOnInit(): void {
    this.peopleId = this.route.snapshot.paramMap.get('id')
    this.detailData$ = this.httpService.getData<People>("/api/people/" + this.peopleId)
  }

  protected readonly JSON = JSON;
}
