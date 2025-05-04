import { useState, useCallback } from "react";
import {
  createFlow,
  deleteAllFlows,
  deleteFlowById,
  getFlowById,
  updateFlow,
  uploadFile,
} from "../api/MoneyFlowsApi";
import { MoneyFlow } from "../types/MoneyFlow";
import { FlowView } from "../components/FlowView";
import { ManagingTable } from "../components/ManagingTable";
import "../styles/managing.css";

export function ManagingPage() {
  const [flowId, setFlowId] = useState("");
  const [flow, setFlow] = useState<MoneyFlow | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [newFlow, setNewFlow] = useState<MoneyFlow>({
    id: "",
    date: "",
    description: "",
    amount: 0,
    additionalInfo: "",
    accountId: "",
    categoryId: "",
    categoryName: "",
  });

  const handleGetFlow = useCallback(async () => {
    if (!flowId) return;
    setIsLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      const data = await getFlowById(flowId);
      setFlow(data);
    } catch (e) {
      console.error(e);
      setError("Не удалось загрузить транзакцию.");
      setFlow(null);
    } finally {
      setIsLoading(false);
    }
  }, [flowId]);

  const handleCreateFlow = useCallback(async () => {
    if (!newFlow.accountId) {
      setError("Нужно указать accountId");
      return;
    }
    setIsLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      const created = await createFlow(newFlow);
      setSuccessMessage(`Создана транзакция с id ${created.id}`);
    } catch (e) {
      console.error(e);
      setError("Ошибка при создании транзакции.");
    } finally {
      setIsLoading(false);
    }
  }, [newFlow]);

  const handleUpdateFlow = useCallback(async () => {
    if (!newFlow.id) {
      setError("Код транзакции не указан.");
      return;
    }
    setIsLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      const updatedFlow = await updateFlow(newFlow);
      setSuccessMessage(`Транзакция ${updatedFlow.id} обновлена.`);
    } catch (e) {
      console.error(e);
      setError("Ошибка при обновлении транзакции.");
    } finally {
      setIsLoading(false);
    }
  }, [newFlow]);

  const handleDeleteFlow = useCallback(async () => {
    if (!flowId) return;
    setIsLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      await deleteFlowById(flowId);
      setSuccessMessage(`Транзакция ${flowId} удалена.`);
    } catch (e) {
      console.error(e);
      setError("Не удалось удалить транзакцию.");
    } finally {
      setIsLoading(false);
    }
  }, [flowId]);

  const handleDeleteAll = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      await deleteAllFlows();
      setSuccessMessage("Все транзакции удалены.");
    } catch (e) {
      console.error(e);
      setError("Ошибка при удалении всех транзакций.");
    } finally {
      setIsLoading(false);
    }
  }, []);

  const handleUploadFile = useCallback(
    async (e: React.ChangeEvent<HTMLInputElement>) => {
      const file = e.target.files?.[0];
      if (!file) return;
      setIsLoading(true);
      setError(null);
      setSuccessMessage(null);
      try {
        await uploadFile(file);
        setSuccessMessage("Файл успешно загружен.");
      } catch (e) {
        console.error(e);
        setError("Ошибка при загрузке файла.");
      } finally {
        setIsLoading(false);
      }
    },
    []
  );

  return (
    <div className="managing-main">
      <h2>Управление транзакциями</h2>

      {error && <div style={{ color: "red" }}>{error}</div>}
      {successMessage && (
        <div style={{ color: "lightgreen" }}>{successMessage}</div>
      )}

      <div className="managing-columns">
        <div>
          <div className="managing-sub">
            <h3>📤 Загрузить файл с транзакциями</h3>
            <input
              type="file"
              accept=".xlsx,.xls"
              onChange={handleUploadFile}
              disabled={isLoading}
            />
          </div>

          <div className="managing-sub">
            <h3>📥 Получить / ❌ Удалить транзакцию по коду</h3>
            <table>
              <tbody>
                <tr>
                  <td>
                    <label>🆔 Код транзакции:</label>
                  </td>
                  <td>
                    <input
                      value={flowId}
                      onChange={(e) => setFlowId(e.target.value)}
                      type="text"
                      disabled={isLoading}
                    />
                    <span
                      onClick={async () => {
                        const text = await navigator.clipboard.readText();
                        setFlowId(text);
                      }}

                      className="paste-button"
                    >
                      📤
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
            <div>
              <button onClick={handleGetFlow} disabled={isLoading || !flowId}>
                Получить
              </button>
              <button
                onClick={handleDeleteFlow}
                disabled={isLoading || !flowId}
              >
                Удалить
              </button>
            </div>
            {flow && <FlowView flow={flow} />}
          </div>

          <div className="managing-sub">
            <h3>❌ Удалить все транзакции</h3>
            <button
              onClick={handleDeleteAll}
              disabled={isLoading}
              style={{ backgroundColor: "#c00" }}
            >
              Удалить все
            </button>
          </div>
        </div>

        <div>
          <div className="managing-sub">
            <h3> 🆕 Создать / 🔄 Обновить транзакцию</h3>
            <ManagingTable
              flow={newFlow}
              disabled={isLoading}
              onChange={setNewFlow}
              onCreate={handleCreateFlow}
              onUpdate={handleUpdateFlow}
              isLoading={isLoading}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
