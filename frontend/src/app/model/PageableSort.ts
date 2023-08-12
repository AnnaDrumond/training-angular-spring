export interface PageableSortableView<T> {
  content: Array<T>;
  totalPages: number;
  pageable: Pageable;
  totalElements: number;
  first: boolean;
  numberOfElements: number;
  size: number;
  empty: boolean;
}

interface Pageable {
  sort: Sort;
  pageSize: number;
  pageNumber: number;
  offset: number;
  unpaged: boolean;
  paged: boolean;
}

interface Sort {
  sorted: boolean;
  unsorted: boolean;
  direction: string;
  property: string;
  empty: boolean;
}
