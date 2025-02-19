export interface IPageResponse<T> {
  content: T[]; // The actual data (list of items)
  number: number; // Current page number
  size: number; // Number of items per page
  totalElements: number; // Total number of items across all pages
  totalPages: number; // Total number of pages
  first: boolean; // Whether this is the first page
  last: boolean; // Whether this is the last page
}
