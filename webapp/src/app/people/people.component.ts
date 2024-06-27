import {Component, EventEmitter, inject} from '@angular/core';
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalModule} from "ng-zorro-antd/modal";
import {PeopleFormComponent} from "../forms/people-form/people-form.component";
import {People, PeopleList} from "../models/people.model";
import {PeopleItemComponent} from "../people-item/people-item.component";
import {HttpService} from "../services/http.service";
import {Observable, of} from "rxjs";
import {AsyncPipe} from "@angular/common";
import {NzPaginationModule} from "ng-zorro-antd/pagination";
import {ActivatedRoute} from "@angular/router";

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
    NzPaginationModule
  ]
})


export class PeopleComponent {
  listPeople$: Observable<PeopleList> | undefined;
  showCreateForm = false
  httpService = inject(HttpService)
  message = inject(NzMessageService)
  route = inject(ActivatedRoute);
  page : number = 1;
  size : number = 10;

  ngOnInit() {
    this.page = this.route.snapshot.queryParams['page'] || 1;
    this.size = this.route.snapshot.queryParams['size'] || 10;
    this.listPeople$ = this.httpService.getData<PeopleList>('/api/people',{page : 1 , size : 10})
    // this.listPeople$ = of([{id: '123', username: "Sypv", aka: "test" , avatar :"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJVmhl_Mnerm8pFZzRtyH36VNOnYkoO9VjzA&s"},
    //   {id: '33', username: "hola", aka: "aaa" , avatar : "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPMYnk7KEzIj0KjYOQuShR_9l64OMpzm0F_w&s"}])
  }

  onPageChange = (page : number) => {
    this.page = page
  }
  onSizeChange = (size : number) => {
    this.size = size
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
  handleImport = (event :  any) => {
    console.log('read file',event.target?.files)
    if(event.target.files.length > 0)
    {
      const formData = new FormData();
      formData.append("file" , event.target.files[0])
      this.httpService.postData("/api/people/import", formData).subscribe(
        {
          next: () => {
            this.message.success("Import people success")
            this.ngOnInit()
          },
          error: () => {
            this.message.error("Import people fail")
          }
        }
      )
    }

  }
}
