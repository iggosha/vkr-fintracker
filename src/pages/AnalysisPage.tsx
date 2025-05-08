import { useCallback, useEffect, useRef, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { getAllClients } from "../api/ClientsApi";
import { getOutflowsByCategories, getForecast } from "../api/AnalysisApi";
import { getAllCategories } from "../api/CategoriesApi";
import {
  getMonthlyInflowsAndOutflows,
  getTotalInflowAndOutflow,
} from "../api/AnalysisApi";
import { FlowsAnalysisContent } from "../components/FlowsAnalysisContent";

import { Client } from "../types/Client";
import { InflowsAndOutflows } from "../types/InflowsAndOutflows";
import { Category } from "../types/Category";
import { CategoriesAnalysisContent } from "../components/CategoriesAnalysisContent";
import { ForecastContent } from "../components/ForecastContent";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend,
} from "chart.js";
import "../styles/analysis.css";

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

export function AnalysisPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [outflows, setOutflows] = useState<Record<string, number>>({});
  const [forecast, setForecast] = useState<Record<string, number>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const [monthlyFlows, setMonthlyFlows] = useState<
    Record<string, InflowsAndOutflows>
  >({});
  const [totalFlow, setTotalFlow] = useState<InflowsAndOutflows | null>(null);

  const urlClientId = searchParams.get("clientId") || "";
  const urlFrom = searchParams.get("from") || "";
  const urlTo = searchParams.get("to") || "";
  const urlMonthAmount = parseInt(searchParams.get("monthAmount") || "3", 10);
  const urlStrategy = searchParams.get("strategyType") || "AVG";
  const [monthAmount, setMonthAmount] = useState(urlMonthAmount);
  const [strategyType, setStrategyType] = useState(urlStrategy);
  const [clientId, setClientId] = useState(urlClientId);
  const [from, setFrom] = useState(urlFrom);
  const [to, setTo] = useState(urlTo);
  const didInitialLoad = useRef(false);

  const validateDateRange = (f: string, t: string) => {
    if (!f || !t) return true;
    return new Date(f) <= new Date(t);
  };

  const loadOutflows = useCallback(async () => {
    if (!clientId || !validateDateRange(from, to)) {
      setOutflows({});
      return;
    }

    setIsLoading(true);
    try {
      const data = await getOutflowsByCategories(clientId, from, to);
      setOutflows(data);
    } catch (e) {
      console.error(e);
      alert("Не удалось загрузить данные анализа.");
    } finally {
      setIsLoading(false);
    }
  }, [clientId, from, to]);

  const loadForecast = useCallback(async () => {
    if (!clientId) {
      setForecast({});
      return;
    }

    try {
      const data = await getForecast(clientId, monthAmount, strategyType);
      setForecast(data);
    } catch (e) {
      console.error(e);
      alert("Не удалось загрузить прогноз.");
    }
  }, [clientId, monthAmount, strategyType]);

  const applyFilters = () => {
    const params = new URLSearchParams();
    if (clientId) params.set("clientId", clientId);
    if (from) params.set("from", from);
    if (to) params.set("to", to);
    if (monthAmount) params.set("monthAmount", monthAmount.toString());
    if (strategyType) params.set("strategyType", strategyType);
    setSearchParams(params, { replace: true });
    loadOutflows();
    loadForecast();
    loadFlows();
  };

  const loadFlows = useCallback(async () => {
    if (!clientId || !validateDateRange(from, to)) {
      setMonthlyFlows({});
      setTotalFlow(null);
      return;
    }

    setIsLoading(true);

    try {
      const [monthly, total] = await Promise.all([
        getMonthlyInflowsAndOutflows(clientId, from, to),
        getTotalInflowAndOutflow(clientId, from, to),
      ]);
      setMonthlyFlows(
        Object.fromEntries(
          Object.entries(monthly).map(([k, v]) => [
            k,
            { inflows: v.inflows, outflows: v.outflows },
          ])
        )
      );
      setTotalFlow({ inflows: total.inflows, outflows: total.outflows });
    } catch (e) {
      console.error(e);
      alert("Не удалось загрузить данные доходов/расходов.");
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
      .catch((error) => {
        console.error("Ошибка при инициализации данных: " + error);
        alert("Ошибка при инициализации данных: " + error);
      });
  }, []);

  useEffect(() => {
    if (!didInitialLoad.current) {
      didInitialLoad.current = true;
      setClientId(urlClientId);
      setFrom(urlFrom);
      setTo(urlTo);
      setMonthAmount(urlMonthAmount);
      setStrategyType(urlStrategy);
      loadOutflows();
      loadForecast();
      loadFlows();
    }
  }, [
    urlClientId,
    urlFrom,
    urlTo,
    urlMonthAmount,
    urlStrategy,
    loadOutflows,
    loadForecast,
    loadFlows,
  ]);

  return (
    <div className="analysis-main">
      <div>
        <div>Прогнозирование изменений баланса</div>

        <div>
          <select
            value={clientId}
            onChange={(e) => setClientId(e.target.value)}
            disabled={isLoading}
          >
            <option value="">👤 Выберите клиента</option>
            {clients.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </select>

          <input
            type="number"
            min={1}
            max={24}
            value={monthAmount}
            onChange={(e) => setMonthAmount(parseInt(e.target.value, 10))}
            placeholder="Месяцев"
          />

          <select
            value={strategyType}
            onChange={(e) => setStrategyType(e.target.value)}
          >
            <option value="AVG">AVG</option>
            <option value="LIN">LIN</option>
            <option value="EXP">EXP</option>
          </select>

          <button onClick={applyFilters} disabled={isLoading || !clientId}>
            Применить
          </button>
        </div>

        {Object.keys(forecast).length > 0 && (
          <ForecastContent forecast={forecast} monthAmount={monthAmount} />
        )}
      </div>

      <div>
        <div>Анализ расходов по категориям</div>
        <select
          value={clientId}
          onChange={(e) => setClientId(e.target.value)}
          disabled={isLoading}
        >
          <option value="">👤 Выберите клиента</option>
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

        <button onClick={applyFilters} disabled={isLoading || !clientId}>
          Применить
        </button>

        <CategoriesAnalysisContent
          outflows={outflows}
          isLoading={isLoading}
          clientId={clientId}
          categories={categories}
        />
      </div>
      <div>
        <div>Анализ доходов и расходов</div>
        <div style={{ marginBottom: "0.5rem" }}>
          <select
            value={clientId}
            onChange={(e) => setClientId(e.target.value)}
            disabled={isLoading}
          >
            <option value="">👤 Выберите клиента</option>
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

          <button onClick={applyFilters} disabled={isLoading || !clientId}>
            Применить
          </button>
        </div>

        {Object.keys(monthlyFlows).length > 0 && totalFlow && (
          <FlowsAnalysisContent
            monthlyFlows={monthlyFlows}
            totalFlow={totalFlow}
          />
        )}
      </div>
    </div>
  );
}
