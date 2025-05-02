import { useEffect, useState, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { getAllClients } from "../api/ClientsApi";
import { getAllMoneyFlows } from "../api/MoneyFlowsApi";
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
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [searchParams, setSearchParams] = useSearchParams();

  const clientId = searchParams.get("clientId") || "";
  const categoryId = searchParams.get("categoryId") || undefined;
  const rawPage = parseInt(searchParams.get("page") || "1", 10);
  const page = Math.max(rawPage - 1, 0);

  const updateParam = useCallback(
    (key: string, value: string | undefined) => {
      const newParams = new URLSearchParams(searchParams);
      if (value === undefined || value === "") {
        newParams.delete(key);
      } else {
        newParams.set(key, value);
      }
      setSearchParams(newParams, { replace: true });
    },
    [searchParams, setSearchParams]
  );

  useEffect(() => {
    const fetchInitialData = async () => {
      setIsLoading(true);
      setError(null);
      try {
        const [clientsData, categoriesData] = await Promise.all([
          getAllClients(),
          getAllCategories(),
        ]);
        setClients(clientsData);
        setCategories(categoriesData);
      } catch (error) {
        console.error("Ошибка при инициализации данных:", error);
        setError(
          "Не удалось загрузить клиентов или категории. Попробуйте позже."
        );
      } finally {
        setIsLoading(false);
      }
    };

    fetchInitialData();
  }, []);

  useEffect(() => {
    const loadFlows = async () => {
      if (!clientId) {
        setFlows([]);
        return;
      }
      setIsLoading(true);
      setError(null);
      try {
        const data = await getAllMoneyFlows(clientId, page, 10, categoryId);
        setFlows(data);
      } catch (error) {
        console.error("Ошибка при загрузке транзакций:", error);
        setError("Не удалось загрузить транзакции. Попробуйте позже.");
      } finally {
        setIsLoading(false);
      }
    };

    loadFlows();
  }, [clientId, categoryId, page]);

  return (
    <div className="flows-main">
      <select
        value={clientId}
        onChange={(e) => updateParam("clientId", e.target.value)}
        disabled={isLoading}
      >
        <option value="">Выберите клиента 👔</option>
        {clients.map((client) => (
          <option key={client.id} value={client.id}>
            {client.name}
          </option>
        ))}
      </select>

      <select
        value={categoryId || ""}
        onChange={(e) => updateParam("categoryId", e.target.value || undefined)}
        disabled={isLoading}
      >
        <option value="">Все категории</option>
        {categories.map((category) => (
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        ))}
      </select>

      {error && <div>{error}</div>}

      {isLoading ? (
        <div>Загрузка...</div>
      ) : (
        <>
          <MoneyFlowTable flows={flows} />

          <div>
            <button
              onClick={() => updateParam("page", String(page))}
              disabled={page === 0 || isLoading}
            >
              Назад
            </button>
            <span>Страница {page + 1}</span>
            <button
              onClick={() => updateParam("page", String(page + 2))}
              disabled={isLoading}
            >
              Вперёд
            </button>
          </div>
        </>
      )}
    </div>
  );
}
