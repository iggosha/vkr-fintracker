// src/components/FlowFormTable.tsx
import React from "react";
import { MoneyFlow } from "../types/MoneyFlow";

interface Props {
  flow: MoneyFlow;
  disabled?: boolean;
  onChange: (newFlow: MoneyFlow) => void;
}

export const ManagingMoneyFlowTable: React.FC<Props> = ({
  flow,
  disabled = false,
  onChange
}) => {
  const handleFieldChange = <K extends keyof MoneyFlow>(
    field: K,
    value: MoneyFlow[K]
  ) => {
    onChange({ ...flow, [field]: value });
  };

  return (
    <table>
      <tbody>
        <tr>
          <td>
            <label>Код счёта (созд.):</label>
          </td>
          <td>
            <input
              type="text"
              value={flow.accountId}
              onChange={(e) => handleFieldChange("accountId", e.target.value)}
              disabled={disabled}
            />
          </td>
        </tr>
        <tr>
          <td>
            <label>Код транзакции (обн.):</label>
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
        <tr>
          <td>
            <label>Дата:</label>
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
            <label>Описание:</label>
          </td>
          <td>
            <input
              type="text"
              value={flow.description}
              onChange={(e) => handleFieldChange("description", e.target.value)}
              disabled={disabled}
            />
          </td>
        </tr>
        <tr>
          <td>
            <label>Сумма:</label>
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
            <label>Доп. информация:</label>
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
            <label>Название категории:</label>
          </td>
          <td>
            <input
              type="text"
              value={flow.categoryName}
              onChange={(e) =>
                handleFieldChange("categoryName", e.target.value)
              }
              disabled={disabled}
            />
          </td>
        </tr>
      </tbody>
    </table>
  );
};
