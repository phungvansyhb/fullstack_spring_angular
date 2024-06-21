import {Component, inject, Input} from '@angular/core';
import {People} from "../../models/people.model";
import {FormsModule} from "@angular/forms";
import {HttpService} from "../../services/http.service";
import {NzMessageService} from "ng-zorro-antd/message";

@Component({
  selector: 'people-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './people-form.component.html',
})


export class PeopleFormComponent {
  @Input() initValue: People | undefined = undefined
  formData : People  = {
    id : '',
    username : '',
    aka : '',
    address : '',
    birthday : '',
    description : ''
  }
  constructor(private httpService: HttpService , private message: NzMessageService) {}

  handleSubmit(data  : People){
    this.httpService.editData<People>('/api/people/' + this.formData.id, data).subscribe({
      next: () => {
        this.message.success("Cap nhap people thanh cong")

      },
      error : ()=>{
        this.message.error("Cap nhap people that bai")
      }
    })
  }
}
