import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend);

interface ForecastContentProps {
  forecast: Record<string, number>;
  monthAmount: number;
}

export function ForecastContent({
  forecast,
  monthAmount,
}: ForecastContentProps) {
  const generateBackgroundColors = () => {
    const values = Object.values(forecast);
    const totalBars = values.length;
    const forecastBars = monthAmount;

    return values.map((value, index) => {
      if (index >= totalBars - forecastBars) {
        return "#fff";
      } else if (value < 0) {
        return "#f00";
      } else {
        return "#3c9";
      }
    });
  };

  const chartData = {
    labels: Object.keys(forecast),
    datasets: [
      {
        label: "Прогноз изменения баланса",
        data: Object.values(forecast),
        backgroundColor: generateBackgroundColors(),
      },
    ],
  };

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>Месяц</th>
            <th>Сумма изменений</th>
          </tr>
        </thead>
        <tbody>
          {Object.entries(forecast).map(([label, value]) => (
            <tr key={label}>
              <td>{label}</td>
              <td>{value}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="bars">
        <Bar data={chartData} />
      </div>
    </div>
  );
}
