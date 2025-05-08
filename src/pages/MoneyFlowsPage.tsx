import { useEffect, useState, useCallback } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { getAllClients } from "../api/ClientsApi";
import { getAllMoneyFlows } from "../api/MoneyFlowsApi";
import { getAllCategories } from "../api/CategoriesApi";
import { MoneyFlow } from "../types/MoneyFlow";
import { Client } from "../types/Client";
import { Category } from "../types/Category";
import { MoneyFlowTable } from "../components/MoneyFlowTable";
import { Page } from "../types/Page";
import "../styles/flows.css";

export function MoneyFlowsPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [flowPage, setFlowPage] = useState<Page<MoneyFlow> | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();

  const clientId = searchParams.get("clientId") || "";
  const categoryId = searchParams.get("categoryId") || undefined;
  const rawPage = parseInt(searchParams.get("page") || "1", 10);
  const page = Math.max(rawPage - 1, 0);

  const updateParam = useCallback(
    (key: string, value: string | undefined) => {
      const newParams = new URLSearchParams(searchParams);
      if (!value) {
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
      try {
        const [clientsData, categoriesData] = await Promise.all([
          getAllClients(),
          getAllCategories(),
        ]);
        setClients(clientsData);
        setCategories(categoriesData);
      } catch (error) {
        alert("Ошибка при инициализации данных: " + error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchInitialData();
  }, []);

  useEffect(() => {
    const loadFlows = async () => {
      if (!clientId) {
        setFlowPage(null);
        return;
      }
      setIsLoading(true);
      try {
        const pageData = await getAllMoneyFlows(clientId, page, 10, categoryId);
        setFlowPage(pageData);
      } catch (error) {
        alert("Не удалось загрузить транзакции: " + error);
      } finally {
        setIsLoading(false);
      }
    };

    loadFlows();
  }, [clientId, categoryId, page]);

  const renderPageNumbers = () => {
    if (!flowPage) return null;

    const totalPages = flowPage.page.totalPages;
    const currentPage = flowPage.page.number;
    const pagesToShow = [];
    const maxButtons = 5;

    if (totalPages <= maxButtons) {
      for (let i = 0; i < totalPages; i++) {
        pagesToShow.push(i);
      }
    } else {
      const start = Math.max(0, currentPage - 1);
      const end = Math.min(totalPages, currentPage + 2);

      if (start > 0) pagesToShow.push(0);
      if (start > 1) pagesToShow.push(-1);

      for (let i = start; i < end; i++) {
        pagesToShow.push(i);
      }

      if (end < totalPages - 1) pagesToShow.push(-1);
      if (end < totalPages) pagesToShow.push(totalPages - 1);
    }

    return (
      <div className="pagination">
        {pagesToShow.map((p, i) =>
          p === -1 ? (
            <span key={i}>...</span>
          ) : (
            <button
              key={p}
              className={p === currentPage ? "active" : ""}
              onClick={() => updateParam("page", String(p + 1))}
            >
              {p + 1}
            </button>
          )
        )}
      </div>
    );
  };

  const updateParams = useCallback(
    (params: Record<string, string | undefined>) => {
      const newParams = new URLSearchParams(searchParams);
      for (const [key, value] of Object.entries(params)) {
        if (!value) {
          newParams.delete(key);
        } else {
          newParams.set(key, value);
        }
      }
      setSearchParams(newParams, { replace: true });
    },
    [searchParams, setSearchParams]
  );

  return (
    <div className="flows-main">
      <select
        value={clientId}
        onChange={(e) => updateParam("clientId", e.target.value)}
        disabled={isLoading}
      >
        <option value="">👤 Выберите клиента</option>
        {clients.map((client) => (
          <option key={client.id} value={client.id}>
            {client.name}
          </option>
        ))}
      </select>

      <select
        value={categoryId || ""}
        onChange={(e) => {
          updateParams({
            categoryId: e.target.value || undefined,
            page: "1",
          });
        }}
        disabled={isLoading}
      >
        <option value="">🅰️ Все категории</option>
        {categories.map((category) => (
          <option key={category.id} value={category.id}>
            {category.name}
          </option>
        ))}
      </select>

      {isLoading && <div>Загрузка...</div>}

      <div className="pagination-controls">
        <button
          onClick={() => updateParam("page", String(page))}
          disabled={page === 0}
        >
          Назад
        </button>
        {renderPageNumbers()}
        <button
          onClick={() => updateParam("page", String(page + 2))}
          disabled={flowPage ? page + 1 >= flowPage.page.totalPages : true}
        >
          Вперёд
        </button>
      </div>

      <MoneyFlowTable flows={flowPage?.content ?? []} clientId={clientId} />
      <div style={{ margin: "50px" }}>
        <Link to={`/analysis?clientId=${clientId}`}>Перейти к анализу</Link>
      </div>
    </div>
  );
}
