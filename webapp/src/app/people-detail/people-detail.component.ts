import { AsyncPipe, NgOptimizedImage, UpperCasePipe } from "@angular/common";
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { People } from "../models/people.model";
import { HttpService } from "../services/http.service";
import { NzIconDirective } from "ng-zorro-antd/icon";
import { NzButtonComponent } from "ng-zorro-antd/button";
import { NzModalComponent, NzModalContentDirective, NzModalModule } from "ng-zorro-antd/modal";
import { MailFormComponent } from "../forms/mail-form/mail-form.component";

@Component({
  selector: 'app-people-detail',
  standalone: true,
  imports: [
    AsyncPipe,
    NgOptimizedImage,
    UpperCasePipe,
    NzIconDirective,
    NzModalContentDirective,
    NzModalComponent,
    NzModalModule,
    MailFormComponent
  ],
  templateUrl: './people-detail.component.html',
})
export class PeopleDetailComponent implements OnInit {


  httpService = inject(HttpService)
  route = inject(ActivatedRoute)
  router = inject(Router)
  detailData: People | undefined
  peopleId: string | undefined | null

  showSendMailForm: boolean = false;
  handleSendMail: any;

  toggleSendMail(){
    this.showSendMailForm = !this.showSendMailForm
  }

  navigateHome() {
    this.router.navigate(['/people'])
  }
  callData = () => {
    this.httpService.getData<People>("/api/people/" + this.peopleId).subscribe({
      next: data => {
        this.detailData = data
      }
    })
  }
  ngOnInit(): void {
    this.peopleId = this.route.snapshot.paramMap.get('id')
    this.callData()
  }
  reaction = (reactType: 'LIKE' | 'DISLIKE') => {
    this.httpService.editData('/api/people/react/' + this.detailData?.id, { reactType }).subscribe({
      next: () => {
        this.callData()
      }
    })
  }
}
