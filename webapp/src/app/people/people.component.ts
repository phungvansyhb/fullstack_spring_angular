import {Component, inject} from '@angular/core';
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalModule} from "ng-zorro-antd/modal";
import {PeopleFormComponent} from "../forms/people-form/people-form.component";
import {People, PeopleList} from "../models/people.model";
import {PeopleItemComponent} from "../people-item/people-item.component";
import {HttpService} from "../services/http.service";
import {Observable, of} from "rxjs";
import {AsyncPipe} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-people',
  standalone: true,
  templateUrl: './people.component.html',
  imports: [PeopleItemComponent,
    NzButtonComponent,
    NzModalContentDirective,
    NzModalComponent,
    NzModalModule,
    PeopleFormComponent,
    AsyncPipe,
  ]
})


export class PeopleComponent {
  listPeople$: Observable<PeopleList> | undefined;
  showCreateForm = false
  httpService = inject(HttpService)
  message = inject(NzMessageService)

  ngOnInit() {
    // this.listPeople$ = this.httpService.getData<PeopleList>('/api/people')
    this.listPeople$ = of([{id: '123', username: "Sypv", aka: "test"}, {id: '33', username: "hola", aka: "aaa"}])
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
