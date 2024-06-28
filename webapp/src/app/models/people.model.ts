import {PaginationList} from "./PaginationList";

export type People ={
  id : string ,
  username : string ,
  aka : string ,
  address : string ,
  birthday : string
  description : string
  avatar : string
}

export type PeopleList = PaginationList<Omit<People, 'description'|'address'|'birthday'>>

