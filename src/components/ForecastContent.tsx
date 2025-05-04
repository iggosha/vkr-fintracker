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
        return "#39c";
      } else if (value < 0) {
        return "#c93";
      } else {
        return "#3c9";
      }
    });
  };

  const totalChange = Object.values(forecast).reduce(
    (sum, value) => sum + value,
    0
  ).toFixed(2);

  const chartData = {
    labels: Object.keys(forecast),
    datasets: [
      {
        label: "Прогноз изменения баланса",
        data: Object.values(forecast),
        backgroundColor: generateBackgroundColors(),
        barThickness: 80,
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
          text: "Сумма изменений",
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
          generateLabels: () => [
            {
              text: "Положительные изменения",
              fillStyle: "#3c9",
              fontColor: "#ddd",
              hidden: false,
              index: 0,
            },
            {
              text: "Отрицательные изменения",
              fillStyle: "#c93",
              fontColor: "#ddd",
              hidden: false,
              index: 1,
            },
            {
              text: "Прогнозируемые изменения",
              fillStyle: "#39c",
              fontColor: "#ddd",
              hidden: false,
              index: 2,
            },
          ],
        },
        onClick: () => {},
      },
    },
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
      <div style={{fontSize: "x-large", marginTop: "25px"}}>
        Суммарное изменение: {totalChange}
      </div>
      <div className="bars">
        <Bar data={chartData} options={options} />
      </div>
    </div>
  );
}
