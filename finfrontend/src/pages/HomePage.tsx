import React from "react";
import { Link } from "react-router-dom";
import "../styles/home.css";

const HomePage: React.FC = () => {
  return (
    <div className="home-main">
      <h1 style={{textAlign: "center"}}>Fintracker — АИС для учёта и прогнозирования личных финансов</h1>
      <p>
        Это приложение позволяет отслеживать финансовые потоки, анализировать
        расходы и доходы, а также строить прогнозы на основе исторических
        данных.
      </p>

      <h3>Доступные страницы:</h3>

      <section>
        <h4>
          <Link to="/analysis">📊 Анализ расходов и доходов, прогнозирование</Link>
        </h4>
        <ul>
          <li>Просмотр расходов по категориям за выбранный период.</li>
          <li>Анализ доходов и расходов по месяцам.</li>
          <li>Получение общей суммы доходов и расходов.</li>
          <li>Прогноз изменения баланса на основе исторических данных.</li>
        </ul>
      </section>

      <section>
        <h4>
          <Link to="/flows">🧾 Просмотр транзакций</Link>
        </h4>
        <ul>
          <li>Получение списка транзакций клиента с пагинацией.</li>
          <li>Фильтрация списка транзакций по категориям.</li>
        </ul>
      </section>

      <section>
        <h4>
          <Link to="/manage">🔧 Управление транзакциями</Link>
        </h4>
        <ul>
          <li>Создание и обновление транзакции.</li>
          <li>Получение и удаление транзакции по её коду (ID).</li>
          <li>Загрузка списка транзакций из Excel.</li>
          <li>Удаление всех транзакций.</li>
        </ul>
      </section>

      <h3>Технические детали</h3>
      <ul>
        <li>Frontend: React, TypeScript, ChartJS, Vite</li>
        <li>Backend: Spring Boot, REST API, OpenAPI</li>
        <li>Database: PostgreSQL</li>
      </ul>
    </div>
  );
};

export default HomePage;
