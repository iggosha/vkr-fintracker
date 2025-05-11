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

export async function getFlowById(id: string): Promise<MoneyFlow> {
  const response = await api.get<MoneyFlow>(`/flows/${id}`);
  return response.data;
}

export async function createFlow(flow: MoneyFlow): Promise<MoneyFlow> {
  const response = await api.post<MoneyFlow>(
    `/accounts/${flow.accountId}/flows`,
    flow
  );
  return response.data;
}

export async function updateFlow(flow: MoneyFlow): Promise<MoneyFlow> {
  const response = await api.put<MoneyFlow>(`/flows/${flow.id}`, flow);
  return response.data;
}

export async function deleteFlowById(id: string): Promise<void> {
  await api.delete(`/flows/${id}`);
}

export async function deleteAllFlows(): Promise<void> {
  await api.delete(`/flows`);
}

export async function uploadFile(file: File): Promise<void> {
  const formData = new FormData();
  formData.append("sheetFile", file);

  await api.post(`/flows/parsed-entities`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}
