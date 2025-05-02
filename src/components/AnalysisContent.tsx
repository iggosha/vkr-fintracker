import { Link } from "react-router-dom";
import { Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  TooltipItem,
} from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const COLORS = [
  "#FF6384",
  "#36A2EB",
  "#FFCE56",
  "#4BC0C0",
  "#9966FF",
  "#FF9F40",
  "#BDB76B",
  "#E9967A",
];

interface Props {
  outflows: Record<string, number>;
  isLoading: boolean;
  clientId: string;
  categories: { id: string; name: string }[];
}

export function AnalysisContent({
  outflows,
  isLoading,
  clientId,
  categories,
}: Props) {
  const chartData = {
    labels: Object.keys(outflows),
    datasets: [
      {
        data: Object.values(outflows).map(Math.abs),
        backgroundColor: COLORS,
        borderColor: COLORS,
        borderWidth: 1,
      },
    ],
  };

  const chartOptions = {
    plugins: {
      legend: {
        position: "top" as const,
        labels: {
          color: "#ddd",
          font: {
            size: 16,
            family: "system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif"
          },
          padding: 16,
          boxWidth: 20,
          boxHeight: 20,
        },
      },
      tooltip: {
        callbacks: {
          label: (context: TooltipItem<"pie">) =>
            `${context.label}: ${Number(context.raw).toFixed(2)}`,
        },
      },
    },
    maintainAspectRatio: false,
  };

  if (isLoading) {
    return <div>Загрузка...</div>;
  }

  const entries = Object.entries(outflows);
  if (entries.length === 0) {
    return <div>Нет данных для отображения.</div>;
  }

  return (
    <div>
      <div>
        <table>
          <thead>
            <tr>
              <th>Категория 🅰️</th>
              <th>Сумма 🔟</th>
            </tr>
          </thead>
          <tbody>
            {entries.map(([name, amount]) => {
              const cat = categories.find((c) => c.name === name);
              const link = cat
                ? `/flows?clientId=${clientId}&categoryId=${cat.id}`
                : null;

              return (
                <tr key={name}>
                  <td>{link ? <Link to={link}>{name}</Link> : name}</td>
                  <td>{Math.abs(amount).toFixed(2)}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

      <div className="pie">
        <Pie data={chartData} options={chartOptions} />
      </div>
    </div>
  );
}
