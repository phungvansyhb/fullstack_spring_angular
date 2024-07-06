import { Component, Input, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { Mail } from '../../models/mail.model';
import { HttpService } from '../../services/http.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'mail-form',
  standalone: true,
  imports: [FormsModule, NzButtonComponent, ReactiveFormsModule],
  templateUrl: './mail-form.component.html',
})

export class MailFormComponent {
  httpService = inject(HttpService)
  message = inject(NzMessageService)

  mailForm = new FormGroup(
    {
      subject: new FormControl('', [Validators.required, Validators.minLength(3)]),
      content: new FormControl('')
    }
  )

  handleSubmit = () => {
    this.httpService.postData<Mail>('/api/mail', { to: "phungvansyhbfw@gmail.com", ...this.mailForm.value }).subscribe({
      next: () => {
        this.message.success('Send mail success')
      },
      error: () => {
        this.message.error('Send mail fail')
      },
    })
  }

}
