import { Bar } from "react-chartjs-2";
import { InflowsAndOutflows } from "../types/InflowsAndOutflows";

interface FlowsAnalysisContentProps {
  monthlyFlows: Record<string, InflowsAndOutflows>;
  totalFlow: InflowsAndOutflows;
}

export function FlowsAnalysisContent({
  monthlyFlows,
  totalFlow,
}: FlowsAnalysisContentProps) {
  const months = Object.keys(monthlyFlows);
  const inflows = months.map((m) => monthlyFlows[m].inflows);
  const outflows = months.map((m) => monthlyFlows[m].outflows);

  const chartData = {
    labels: months,
    datasets: [
      {
        label: "Доходы",
        data: inflows,
        backgroundColor: "#3c9",
      },
      {
        label: "Расходы",
        data: outflows,
        backgroundColor: "#c93",
      },
    ],
  };

  const options = {
    scales: {
      x: {
        title: {
          display: true,
          text: "Месяц",
          color: "#ddd",
        },
        ticks: {
          color: "#ddd",
        },
      },
      y: {
        title: {
          display: true,
          text: "Сумма",
          color: "#ddd",
        },
        ticks: {
          color: "#ddd",
        },
      },
    },
    plugins: {
      legend: {
        labels: {
          color: "#ddd",
        },
      },
    },
  };

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>📆 Месяц</th>
            <th>📥 Доходы</th>
            <th>📤 Расходы</th>
          </tr>
        </thead>
        <tbody>
          {months.map((month) => (
            <tr key={month}>
              <td>{month}</td>
              <td>{monthlyFlows[month].inflows}</td>
              <td>{monthlyFlows[month].outflows}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="bars">
        <Bar data={chartData} options={options} />
      </div>

      <div>
        <div>📥 Суммарный доход: {totalFlow.inflows}</div>
        <div>📤 Суммарный расход: {totalFlow.outflows}</div>
      </div>
    </div>
  );
}
