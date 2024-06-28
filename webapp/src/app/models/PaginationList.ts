export type PaginationList<T> = {
  totalPages : number ,
  content : T[],
  size : number,
  totalElements : number
}
