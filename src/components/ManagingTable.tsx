import React, { useState, useEffect } from "react";
import { MoneyFlow } from "../types/MoneyFlow";
import { getAllCategories } from "../api/CategoriesApi";
import { Category } from "../types/Category";

interface Props {
  flow: MoneyFlow;
  disabled?: boolean;
  onChange: (newFlow: MoneyFlow) => void;
  onCreate: () => void;
  onUpdate: () => void;
  isLoading: boolean;
}

export const ManagingTable: React.FC<Props> = ({
  flow,
  disabled = false,
  onChange,
  onCreate,
  onUpdate,
  isLoading,
}) => {
  const [mode, setMode] = useState<"create" | "update">("create");
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    getAllCategories().then((result) => setCategories(result));
  }, []);

  const handleFieldChange = <K extends keyof MoneyFlow>(
    field: K,
    value: MoneyFlow[K]
  ) => {
    onChange({ ...flow, [field]: value });
  };

  return (
    <div>
      <div>
        <label>
          <input
            type="radio"
            value="create"
            checked={mode === "create"}
            onChange={() => {
              setMode("create");
              onChange({ ...flow, id: "" });
            }}
            disabled={isLoading}
          />
          🆕 Создание
        </label>
        <label>
          <input
            type="radio"
            value="update"
            checked={mode === "update"}
            onChange={() => {
              setMode("update");
              onChange({ ...flow, accountId: "" });
            }}
            disabled={isLoading}
          />
          🔄 Обновление
        </label>
      </div>

      <table>
        <tbody>
          {mode === "create" && (
            <tr>
              <td>
                <label>🆔 Код счёта:</label>
              </td>
              <td>
                <input
                  type="text"
                  value={flow.accountId}
                  onChange={(e) =>
                    handleFieldChange("accountId", e.target.value)
                  }
                  disabled={disabled}
                />
              </td>
            </tr>
          )}
          {mode === "update" && (
            <tr>
              <td>
                <label>🆔 Код транзакции:</label>
              </td>
              <td>
                <input
                  type="text"
                  value={flow.id}
                  onChange={(e) => handleFieldChange("id", e.target.value)}
                  disabled={disabled}
                />
              </td>
            </tr>
          )}
          <tr>
            <td>
              <label>📆 Дата:</label>
            </td>
            <td>
              <input
                type="date"
                value={flow.date}
                onChange={(e) => handleFieldChange("date", e.target.value)}
                disabled={disabled}
              />
            </td>
          </tr>
          <tr>
            <td>
              <label>📝 Описание:</label>
            </td>
            <td>
              <input
                type="text"
                value={flow.description}
                onChange={(e) =>
                  handleFieldChange("description", e.target.value)
                }
                disabled={disabled}
              />
            </td>
          </tr>
          <tr>
            <td>
              <label>🔟 Сумма:</label>
            </td>
            <td>
              <input
                type="number"
                value={flow.amount}
                onChange={(e) =>
                  handleFieldChange("amount", parseFloat(e.target.value) || 0)
                }
                disabled={disabled}
              />
            </td>
          </tr>
          <tr>
            <td>
              <label>ℹ️ Доп. информация:</label>
            </td>
            <td>
              <input
                type="text"
                value={flow.additionalInfo}
                onChange={(e) =>
                  handleFieldChange("additionalInfo", e.target.value)
                }
                disabled={disabled}
              />
            </td>
          </tr>
          <tr>
            <td>
              <label>🅰️ Категория:</label>
            </td>
            <td>
              <select
                value={flow.categoryName || ""}
                onChange={(e) =>
                  handleFieldChange("categoryName", e.target.value)
                }
                disabled={disabled}
              >
                <option value="">Выберите категорию</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.name}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </td>
          </tr>
        </tbody>
      </table>

      <div>
        {mode === "create" && (
          <button onClick={onCreate} disabled={isLoading || !flow.accountId}>
            Создать
          </button>
        )}
        {mode === "update" && (
          <button onClick={onUpdate} disabled={isLoading || !flow.id}>
            Обновить
          </button>
        )}
      </div>
    </div>
  );
};
