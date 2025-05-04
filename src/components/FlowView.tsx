import { useState } from "react";
import { MoneyFlow } from "../types/MoneyFlow";

interface FieldProps {
  label: string;
  value: string | number;
  emoji: string;
}

function CopyableField({ label, value, emoji }: FieldProps) {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(value.toString());
      setCopied(true);
      setTimeout(() => setCopied(false), 1000);
    } catch (err) {
      console.error("Не удалось скопировать: ", err);
    }
  };

  return (
    <p style={{ cursor: "pointer" }} onClick={handleCopy}>
      {emoji} <strong>{label}:</strong> {value}
      {copied && <span>📥</span>}
    </p>
  );
}

export function FlowView({ flow }: { flow: MoneyFlow }) {
  return (
    <div style={{ padding: "25px" }}>
      <CopyableField emoji="🆔" label="ID" value={flow.id} />
      <CopyableField emoji="📆" label="Дата" value={flow.date} />
      <CopyableField emoji="📝" label="Описание" value={flow.description} />
      <CopyableField emoji="🔟" label="Сумма" value={flow.amount} />
      <CopyableField
        emoji="ℹ️"
        label="Доп. информация"
        value={flow.additionalInfo}
      />
      <CopyableField emoji="🆔" label="Код счётта" value={flow.accountId} />
      <CopyableField emoji="🅰️" label="Категория" value={flow.categoryName} />
    </div>
  );
}
