import api from "./Api";
import { MoneyFlow } from "../types/MoneyFlow";
import { Page } from "../types/Page";

export async function getAllMoneyFlows(
  clientId: string,
  page: number,
  size: number,
  categoryId?: string
): Promise<Page<MoneyFlow>> {
  const params = new URLSearchParams();
  params.set("page", String(page));
  params.set("size", String(size));
  if (categoryId) {
    params.set("categoryId", categoryId);
  }

  const response = await api.get<Page<MoneyFlow>>(
    `/clients/${clientId}/flows?${params.toString()}`
  );
  return response.data;
}
