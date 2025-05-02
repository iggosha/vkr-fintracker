import api from "./Api";
import { MoneyFlow } from "../types/MoneyFlow";

type GetAllMoneyFlowsParams = {
  page: number;
  size: number;
  categoryId?: string;
};

export async function getAllMoneyFlows(
  clientId: string,
  page: number,
  size: number,
  categoryId?: string
) {
  const params: GetAllMoneyFlowsParams = {
    page,
    size,
    ...(categoryId && { categoryId }),
  };

  const response = await api.get<MoneyFlow[]>(
    `/clients/${clientId}/flows`,
    { params }
  );

  return response.data;
}
