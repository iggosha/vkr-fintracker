import { useState } from "react";
import { MoneyFlow } from "../types/MoneyFlow";
import { Link } from "react-router-dom";

interface Props {
  flows: MoneyFlow[];
  clientId: string;
}

function CopyableCell({ value, allowWrap = false }: { value: string | number, allowWrap?: boolean }) {
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
    <td onClick={handleClick} style={{ cursor: "pointer", whiteSpace: allowWrap ? "normal" : "nowrap" }}>
      {value}
      {copied && <span>📥</span>}
    </td>
  );
}

export function MoneyFlowTable({ flows, clientId }: Props) {
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
          {flows.map((flow) => (
            <tr key={flow.id}>
              <CopyableCell value={flow.id}/>
              <CopyableCell value={flow.date} />
              <CopyableCell value={flow.description} allowWrap={true}/>
              <CopyableCell value={flow.amount} />
              <CopyableCell value={flow.categoryName} />
              <CopyableCell value={flow.accountId} />
              <CopyableCell value={flow.additionalInfo} />
            </tr>
          ))}
        </tbody>
      </table>
      <div>
        <Link to={`/analysis?clientId=${clientId}`}>
          <button>Перейти к анализу</button>
        </Link>
      </div>
    </div>
  );
}
