export type People ={
  id : string ,
  username : string ,
  aka : string ,
  address : string ,
  birthday : string
  description : string
}

export type PeopleList = Omit<People, 'description'|'address'|'birthday'>[]
