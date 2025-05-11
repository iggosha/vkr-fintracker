import { useState } from "react";
import { MoneyFlow } from "../types/MoneyFlow";

interface Props {
  flows: MoneyFlow[];
  clientId: string;
}

function CopyableCell({
  value,
  allowWrap = false,
}: {
  value: string | number;
  allowWrap?: boolean;
}) {
  const [copied, setCopied] = useState(false);

  const handleClick = async () => {
    try {
      await navigator.clipboard.writeText(value.toString());
      setCopied(true);
      setTimeout(() => setCopied(false), 1000);
    } catch (err) {
      console.error("Ошибка копирования: ", err);
    }
  };

  return (
    <td
      onClick={handleClick}
      style={{ cursor: "pointer", whiteSpace: allowWrap ? "normal" : "nowrap" }}
    >
      {value}
      {copied && <span>📥</span>}
    </td>
  );
}
export function MoneyFlowTable({ flows }: Props) {
  const MIN_ROWS = 1;
  const rows = [...flows];

  while (rows.length < MIN_ROWS) {
    rows.push({
      id: "",
      date: "",
      description: "",
      amount: 0,
      categoryId: "",
      categoryName: "",
      accountId: "",
      additionalInfo: "",
    } as MoneyFlow);
  }

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>🆔 Код</th>
            <th>📆 Дата</th>
            <th>📝 Описание</th>
            <th>🔟 Сумма</th>
            <th>🅰️ Категория</th>
            <th>🆔 Код счёта</th>
            <th>ℹ️ Доп. инфо</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((flow, index) => (
            <tr key={index}>
              <CopyableCell value={flow.id} />
              <CopyableCell value={flow.date} />
              <CopyableCell value={flow.description} allowWrap={true} />
              <CopyableCell value={flow.amount} />
              <CopyableCell value={flow.categoryName} />
              <CopyableCell value={flow.accountId} />
              <CopyableCell value={flow.additionalInfo} />
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
