import api from "./Api";
import { Client } from "../types/Client";

export async function getClientById(id: string) {
  const response = await api.get<Client>(`/clients/${id}`);
  return response.data;
}

export async function getAllClients() {
  const response = await api.get<Client[]>("/clients");
  return response.data;
}
