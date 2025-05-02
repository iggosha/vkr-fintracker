import { useEffect, useState, useRef, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { getAllClients } from "../api/ClientsApi";
import { getOutflowsByCategories } from "../api/AnalysisApi";
import { getAllCategories } from "../api/CategoriesApi";
import { Client } from "../types/Client";
import { Category } from "../types/Category";
import { AnalysisContent } from "../components/AnalysisContent";
import "../styles/analysis.css";

export function AnalysisPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [outflows, setOutflows] = useState<Record<string, number>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const [searchParams, setSearchParams] = useSearchParams();
  const urlClientId = searchParams.get("clientId") || "";
  const urlFrom = searchParams.get("from") || "";
  const urlTo = searchParams.get("to") || "";

  const [clientId, setClientId] = useState(urlClientId);
  const [from, setFrom] = useState(urlFrom);
  const [to, setTo] = useState(urlTo);

  const didInitialLoad = useRef(false);

  const validateDateRange = (f: string, t: string) => {
    if (!f || !t) return true;
    return new Date(f) <= new Date(t);
  };

  const loadOutflows = useCallback(async () => {
    if (!clientId) {
      setError(null);
      setOutflows({});
      return;
    }
    if (!validateDateRange(from, to)) {
      setError("Дата «от» не может быть позже даты «до».");
      setOutflows({});
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      const data = await getOutflowsByCategories(clientId, from, to);
      setOutflows(data);
    } catch (e) {
      console.error(e);
      setError("Не удалось загрузить данные анализа. Попробуйте позже.");
      setOutflows({});
    } finally {
      setIsLoading(false);
    }
  }, [clientId, from, to]);

  useEffect(() => {
    Promise.all([getAllClients(), getAllCategories()])
      .then(([clientsData, categoriesData]) => {
        setClients(clientsData);
        setCategories(categoriesData);
      })
      .catch((e) => {
        console.error(e);
        setError("Не удалось загрузить список клиентов или категорий.");
      });
  }, []);

  useEffect(() => {
    if (!didInitialLoad.current) {
      didInitialLoad.current = true;
      if (urlClientId) {
        setClientId(urlClientId);
        setFrom(urlFrom);
        setTo(urlTo);
        loadOutflows();
      }
    }
  }, [urlClientId, urlFrom, urlTo, loadOutflows]);

  const handleApply = () => {
    const params = new URLSearchParams();
    if (clientId) params.set("clientId", clientId);
    if (from) params.set("from", from);
    if (to) params.set("to", to);
    setSearchParams(params, { replace: true });
    loadOutflows();
  };

  return (
    <div className="analysis-main">
      <div>Анализ расходов по категориям</div>

      <div className="filters">
        <select
          value={clientId}
          onChange={(e) => setClientId(e.target.value)}
          disabled={isLoading}
        >
          <option value="">Выберите клиента 👔</option>
          {clients.map((c) => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>

        <input
          type="date"
          value={from}
          onChange={(e) => setFrom(e.target.value)}
          disabled={isLoading}
        />
        <input
          type="date"
          value={to}
          onChange={(e) => setTo(e.target.value)}
          disabled={isLoading}
        />

        <button onClick={handleApply} disabled={isLoading || !clientId}>
          Применить
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      <AnalysisContent
        outflows={outflows}
        isLoading={isLoading}
        clientId={clientId}
        categories={categories}
      />
    </div>
  );
}
