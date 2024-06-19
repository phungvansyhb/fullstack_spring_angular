import {Component, Input} from '@angular/core';
import {People, PeopleList} from "../models/people.model";
import {HttpService} from "../services/http.service";

@Component({
  selector: 'app-people-detail',
  standalone: true,
  imports: [],
  templateUrl: './people-detail.component.html',
})
export class PeopleDetailComponent {
  @Input() people: PeopleList[number] | undefined = undefined;
  detailData: People | undefined = undefined
  isShowDetail = false
  isLoading = false

  constructor(private httpService: HttpService) {
  }

  toggleDetail() {
    this.isLoading = true
    this.isShowDetail = !this.isShowDetail
    this.httpService.getData<People>("/api/people/" + this.people?.id).subscribe({
      next: (data) => {
        this.detailData = data
      },
      complete: () => {
        this.isLoading = false
      }
    })
  }
}
