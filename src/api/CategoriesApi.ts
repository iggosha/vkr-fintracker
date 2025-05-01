import api from "./Api";
import { Category } from "../types/Category";

export async function getCategoryById(id: string) {
  const response = await api.get<Category>(`/categories/${id}`);
  return response.data;
}

export async function getAllCategories() {
  const response = await api.get<Category[]>("/categories");
  return response.data;
}
