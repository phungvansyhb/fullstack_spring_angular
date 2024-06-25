import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { People, PeopleList } from "../models/people.model";
import { HttpService } from "../services/http.service";
import { NzIconDirective } from "ng-zorro-antd/icon";
import { NzPopoverDirective } from "ng-zorro-antd/popover";
import { NzMessageService } from "ng-zorro-antd/message";
import { NzModalComponent, NzModalContentDirective, NzModalModule } from "ng-zorro-antd/modal";
import { NzButtonComponent } from "ng-zorro-antd/button";
import { NgOptimizedImage } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { PeopleFormComponent } from "../forms/people-form/people-form.component";

@Component({
  selector: 'app-people-detail',
  standalone: true,
  imports: [
    NgOptimizedImage,
    NzIconDirective,
    NzPopoverDirective,
    NzModalContentDirective,
    NzModalComponent,
    NzModalModule,
    NzButtonComponent,
    FormsModule,
    PeopleFormComponent
  ],
  styles: [
  ],
  templateUrl: './people-detail.component.html',
})
export class PeopleDetailComponent implements OnInit {
  @Input() people: PeopleList[number] | undefined = undefined;
  @Input() refreshList: () => void = () => { };

  detailData: People | undefined = undefined
  isShowDetail = false
  isLoading = false
  showForm = false
  constructor(private httpService: HttpService, private message: NzMessageService) {
  }

  ngOnInit(): void {
    this.httpService.getData<People>("/api/people/" + this.people?.id).subscribe({
      next: (data) => {
        this.detailData = data
      },
      complete: () => {
        this.isLoading = false
      }
    })
  }
  toggleDetail = () => {
    this.isShowDetail = !this.isShowDetail
  }

  handleDelete = () => {
    this.httpService.deleteData('/api/people/' + this.people?.id).subscribe({
      next: () => {
        this.message.success("Xóa people thành công")
        this.refreshList()
      },
      error: () => {
        this.message.error("Xóa people thất bại")
      }
    })
  }

  toggleShowForm = () => {
    this.showForm = !this.showForm
  }
  handleUpdatePeople = (data: People)=> {
    this.httpService.editData("/api/people/" + this.detailData?.id , data).subscribe(
      {
        next: () => {
          this.message.success("Update people success")
          this.showForm = false
          this.refreshList()
        },
        error: () => {
          this.message.error("Update people fail")
        }
      }
    )
  }


}
