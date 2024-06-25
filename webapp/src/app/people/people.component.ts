import { Component, inject } from '@angular/core';
import { NzButtonComponent } from "ng-zorro-antd/button";
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalComponent, NzModalContentDirective, NzModalModule } from "ng-zorro-antd/modal";
import { PeopleFormComponent } from "../forms/people-form/people-form.component";
import { People, PeopleList } from "../models/people.model";
import { PeopleDetailComponent } from "../people-detail/people-detail.component";
import { HttpService } from "../services/http.service";
import { error } from 'console';

@Component({
  selector: 'app-people',
  standalone: true,
  templateUrl: './people.component.html',
  imports: [PeopleDetailComponent, NzButtonComponent,
    NzModalContentDirective,
    NzModalComponent,
    NzModalModule, PeopleFormComponent]
})


export class PeopleComponent {

  listPeople: PeopleList = []
  showCreateForm = false
  detailData: any;
  httpService = inject(HttpService)
  message = inject(NzMessageService)

  ngOnInit() {
    console.log("cpn init")
    this.httpService.getData<PeopleList>('/api/people').subscribe((data) => {
      this.listPeople = data;
    })
  }
  refreshData = () => {
    this.ngOnInit()
  }
  toggleCreateForm = () => {
    this.showCreateForm = !this.showCreateForm
  }
  handleCreatePeople = (data: People) => {
    this.httpService.postData("/api/people", data).subscribe(
      {
        next: () => {
          this.message.success("Create people success")
          this.ngOnInit()
          this.showCreateForm = false
        },
        error: () => {
          this.message.error("Create people fail")
        }
      }
    )
  }
}
