import {Component, inject, Injectable} from '@angular/core';
import {HttpService} from "../services/http.service";
import {People, PeopleList} from "../models/people.model";
import {PeopleDetailComponent} from "../people-detail/people-detail.component";

@Component({
  selector: 'app-people',
  standalone: true,
  imports: [PeopleDetailComponent],
  templateUrl: './people.component.html',
})


export class PeopleComponent {
  httpService = inject(HttpService);
  listPeople: PeopleList = []

  ngOnInit() {
    this.httpService.getData<PeopleList>('/api/people').subscribe((data) => {
      this.listPeople = data;
    })
  }
}
