export interface Page<T> {
  content: T[];
  totalPages: number;
  number: number;
  size: number;
  totalElements: number;
}
