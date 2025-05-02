import { Link } from "react-router-dom";
import "../../styles/common/header.css";

export function Header() {
  return (
    <header>
      <Link to="/">💳 Fintracker</Link>
      <Link to="/flows">🧾 Транзакции</Link>
      <Link to="/manage">🔧 Управление</Link>
      <Link to="/analysis">📊 Анализ</Link>
    </header>
  );
}
