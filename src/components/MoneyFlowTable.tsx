import { MoneyFlow } from "../types/MoneyFlow";
import { Link } from "react-router-dom";


interface Props {
  flows: MoneyFlow[];
  clientId: string;

}

export function MoneyFlowTable({ flows, clientId }: Props) {
  return (
    <table>
      <thead>
        <tr>
          <th>Код 🆔</th>
          <th>Дата 📆</th>
          <th>Описание 📝</th>
          <th>Сумма 🔟</th>
          <th>
            <Link to={`/analysis?clientId=${clientId}`}>Категория 🅰️</Link>
          </th>
          <th>Доп. инфо 📄</th>
        </tr>
      </thead>
      <tbody>
        {flows.map((flow) => (
          <tr key={flow.id}>
            <td style={{ whiteSpace: "nowrap" }}>{flow.id}</td>
            <td style={{ whiteSpace: "nowrap" }}>{flow.date}</td>
            <td>{flow.description}</td>
            <td style={{ whiteSpace: "nowrap" }}>{flow.amount}</td>
            <td style={{ whiteSpace: "nowrap" }}>{flow.categoryName}</td>
            <td>{flow.additionalInfo}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
