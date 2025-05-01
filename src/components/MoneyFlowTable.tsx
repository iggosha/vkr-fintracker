import { MoneyFlow } from "../types/MoneyFlow";

interface Props {
  flows: MoneyFlow[];
}

export function MoneyFlowTable({ flows }: Props) {
  return (
    <table>
      <thead>
        <tr>
          <th>Дата</th>
          <th>Описание</th>
          <th>Сумма</th>
          <th>Категория</th>
          <th>Доп. информация</th>
        </tr>
      </thead>
      <tbody>
        {flows.map((flow) => (
          <tr key={flow.id}>
            <td>{flow.date}</td>
            <td>{flow.description}</td>
            <td>{flow.amount}</td>
            <td>{flow.categoryName}</td>
            <td>{flow.additionalInfo}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
