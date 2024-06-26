import { Routes } from '@angular/router';
import {PeopleComponent} from "./people/people.component";
import {CategoryComponent} from "./category/category.component";
import {PagenotfoundComponent} from "./pagenotfound/pagenotfound.component";
import {PeopleDetailComponent} from "./people-detail/people-detail.component";

export const routes: Routes = [
  {
    path: 'home',
    title : "Home page",
    component: CategoryComponent,

  },
  {
    path: 'people',
    title : "People page",
    component: PeopleComponent
  },
  {
    path: 'people-detail/:id',
    title : "People detail page",
    component: PeopleDetailComponent
  },

  {
    path: '',
    redirectTo : '/home',
    pathMatch : "full"
  },
  {
    path: '**',
    title : "Page not found",
    component: PagenotfoundComponent
  }
];
