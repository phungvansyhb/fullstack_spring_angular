import { Routes } from '@angular/router';
import {PeopleComponent} from "./people/people.component";
import {CategoryComponent} from "./category/category.component";

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
  }
];
