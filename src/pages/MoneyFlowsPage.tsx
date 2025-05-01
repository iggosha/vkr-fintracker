import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { getAllClients } from "../api/ClientsApi";
import { getAllhMoneyFlows } from "../api/MoneyFlowsApi";
import { getAllCategories } from "../api/CategoriesApi";
import { MoneyFlow } from "../types/MoneyFlow";
import { Client } from "../types/Client";
import { Category } from "../types/Category";
import { MoneyFlowTable } from "../components/MoneyFlowTable";
import "../styles/flows.css";

export function MoneyFlowsPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [flows, setFlows] = useState<MoneyFlow[]>([]);
  const [searchParams, setSearchParams] = useSearchParams();

  const clientId = searchParams.get("clientId") || "";
  const categoryId = searchParams.get("categoryId") || undefined;
  const rawPage = parseInt(searchParams.get("page") || "1", 10);
  const page = Math.max(rawPage - 1, 0);

  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const [clientsData, categoriesData] = await Promise.all([
          getAllClients(),
          getAllCategories(),
        ]);
        setClients(clientsData);
        setCategories(categoriesData);
      } catch (error) {
        console.error("Ошибка при инициализации данных:", error);
        alert("Не удалось загрузить клиентов или категории");
      }
    };

    fetchInitialData();
  }, []);

  useEffect(() => {
    const loadFlows = async () => {
      if (!clientId) return;
      try {
        const data = await getAllhMoneyFlows(clientId, page, 10, categoryId);
        setFlows(data);
      } catch (error) {
        console.error("Ошибка при загрузке транзакций:", error);
        alert("Не удалось загрузить транзакции: " + error);
      }
    };

    loadFlows();
  }, [clientId, categoryId, page]);

const updateParam = (key: string, value: string | undefined) => {
  const newParams = new URLSearchParams(searchParams);
  if (value === undefined || value === "") {
    newParams.delete(key);
  } else {
    newParams.set(key, value);
  }
  setSearchParams(newParams);
};


  return (
    <div className="main">
      <select
        value={clientId}
        onChange={(e) => updateParam("clientId", e.target.value)}
      >
        <option value="">Выберите клиента</option>
        {clients.map((client) => (
          <option key={client.id} value={client.id}>
            {client.name}
          </option>
        ))}
      </select>

      <select
        value={categoryId || ""}
        onChange={(e) => updateParam("categoryId", e.target.value || undefined)}
      >
        <option value="">Все категории</option>
        {categories.map((category) => (
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        ))}
      </select>

      <MoneyFlowTable flows={flows} />

      <div>
        <button
          onClick={() => updateParam("page", String(page))} // page - 1 + 1
          disabled={page === 0}
        >
          Назад
        </button>
        <span>Страница {page + 1}</span>
        <button
          onClick={() => updateParam("page", String(page + 2))} // page + 1 + 1
        >
          Вперёд
        </button>
      </div>
    </div>
  );
}
