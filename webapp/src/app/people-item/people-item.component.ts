import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {People, PeopleList} from "../models/people.model";
import {HttpService} from "../services/http.service";
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzPopoverDirective} from "ng-zorro-antd/popover";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzModalComponent, NzModalContentDirective, NzModalModule} from "ng-zorro-antd/modal";
import {NzButtonComponent} from "ng-zorro-antd/button";
import {NgOptimizedImage} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {PeopleFormComponent} from "../forms/people-form/people-form.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-people-item',
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
  styles: [],
  templateUrl: './people-item.component.html',
})
export class PeopleItemComponent {
  @Input() people: PeopleList['content'][number] | undefined = undefined;
  @Input() refreshList: () => void = () => {
  };
  detailData: People | undefined = undefined
  isLoading = false
  showForm = false

  constructor(private httpService: HttpService, private message: NzMessageService, private router: Router) {
  }

  navigateDetail = () => {
    this.router.navigate(['/people-detail/', this.people?.id])
  }

  reaction = (reactType: 'LIKE' | 'DISLIKE') => {
    this.httpService.editData('/api/people/react/'+this.people?.id , {reactType}).subscribe({
      next : () => {
        this.refreshList()
      }
    })
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
    this.httpService.getData<People>("/api/people/" + this.people?.id).subscribe({
      next: (data) => {
        this.detailData = data
      },
      complete: () => {
        this.isLoading = false
      }
    })
    this.showForm = !this.showForm
  }
  handleUpdatePeople = (data: Partial<People>) => {
    this.httpService.editData("/api/people/" + this.detailData?.id, data).subscribe(
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
