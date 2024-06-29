import { AsyncPipe, NgOptimizedImage, UpperCasePipe } from "@angular/common";
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { People } from "../models/people.model";
import { HttpService } from "../services/http.service";

@Component({
  selector: 'app-people-detail',
  standalone: true,
  imports: [
    AsyncPipe,
    NgOptimizedImage,
    UpperCasePipe
  ],
  templateUrl: './people-detail.component.html',
})
export class PeopleDetailComponent implements OnInit {

  httpService = inject(HttpService)
  route = inject(ActivatedRoute)
  router = inject(Router)
  detailData: People | undefined
  peopleId: string | undefined | null

  navigateHome(){
    this.router.navigate(['/people'])
  }
  ngOnInit(): void {
    this.peopleId = this.route.snapshot.paramMap.get('id')
    this.httpService.getData<People>("/api/people/" + this.peopleId).subscribe({
      next: data => {
        this.detailData = data
      }
    })
  }

  protected readonly JSON = JSON;
}
