import {Component, inject, OnInit} from '@angular/core';
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalComponent, NzModalContentDirective, NzModalModule} from "ng-zorro-antd/modal";
import {PeopleFormComponent} from "../forms/people-form/people-form.component";
import {People, PeopleList} from "../models/people.model";
import {PeopleItemComponent} from "../people-item/people-item.component";
import {HttpService} from "../services/http.service";
import {AsyncPipe} from "@angular/common";
import {NzPaginationModule} from "ng-zorro-antd/pagination";
import {ActivatedRoute, Router} from "@angular/router";

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


export class PeopleComponent implements OnInit {
  listPeople: PeopleList['content'] = [];
  showCreateForm = false
  httpService = inject(HttpService)
  message = inject(NzMessageService)
  route = inject(ActivatedRoute);
  router = inject(Router);
  page: number = 1;
  size: number = 10;
  totalElements = 0;

  handleLoadData = () => {
    this.httpService.getData<PeopleList>('/api/people', {page: this.page - 1, size: this.size}).subscribe(data => {
      this.listPeople = data.content
      this.totalElements = data.totalElements
    })
  }

  ngOnInit() {
    this.page = this.route.snapshot.queryParams['page'] || 1;
    this.size = this.route.snapshot.queryParams['size'] || 10;
    this.handleLoadData()
  }

  onPageChange = (page: number) => {
    if (page !== this.page) {
      this.page = page
      this.router.navigate([], {queryParams: {page: page}}).then(() =>
        this.handleLoadData()
      )
    }

  }
  onSizeChange = (size: number) => {
    if (size !== this.size) {
      this.size = size
      this.router.navigate([], {queryParams: {size: size}}).then(() =>
        this.handleLoadData()
      )
    }

  }

  refreshData = () => {
    this.handleLoadData()
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
  handleImport = (event: any) => {
    console.log('read file', event.target?.files)
    if (event.target.files.length > 0) {
      const formData = new FormData();
      formData.append("file", event.target.files[0])
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
