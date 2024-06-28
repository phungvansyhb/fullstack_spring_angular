import {PaginationList} from "./PaginationList";

export type People ={
  id : string ,
  username : string ,
  aka : string ,
  address : string ,
  birthday : string
  description : string
  avatar : string
  like : number
  dislike : number
}

export type PeopleList = PaginationList<Omit<People, 'description'|'address'|'birthday'>>

