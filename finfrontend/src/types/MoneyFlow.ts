export interface MoneyFlow {
  id: string;
  date: string;
  description: string;
  amount: number;
  additionalInfo: string;
  accountId: string;
  categoryId: string;
  categoryName: string;
}
