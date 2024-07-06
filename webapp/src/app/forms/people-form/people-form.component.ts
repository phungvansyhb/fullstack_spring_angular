import { Component, Input } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { People } from "../../models/people.model";

@Component({
  selector: 'people-form',
  standalone: true,
  imports: [FormsModule, NzButtonComponent],
  templateUrl: './people-form.component.html',
})

export class PeopleFormComponent {
  @Input() initValue: People | undefined = undefined
  @Input() handleOk: (data: Partial<People>) => void = () => { }

  formData = {
    id: '',
    username: '',
    aka: '',
    address: '',
    birthday: '',
    description: '',
    avatar : '',
  }
  ngOnInit() {
    console.log('fomr init ', this.initValue)
    if (this.initValue) {
      this.formData = this.initValue
    }
  }
  handleSubmit = () => {
    this.handleOk(this.formData)
  }
}
