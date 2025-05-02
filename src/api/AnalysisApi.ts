import api from "./Api";

export async function getOutflowsByCategories(
  clientId: string,
  from?: string,
  to?: string
): Promise<Record<string, number>> {
  const params: Record<string, string> = {};
  if (from) params.from = from;
  if (to) params.to = to;

  const response = await api.get<Record<string, number>>(
    `/clients/${clientId}/outflows`,
    { params }
  );
  return response.data;
}
