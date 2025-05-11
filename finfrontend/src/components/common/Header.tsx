import { Link } from "react-router-dom";
import "../../styles/common/header.css";

export function Header() {
  return (
    <header>
      <img src="/logo.svg" style={{ height: "80px" }}></img>
      <Link to="/home">🏠 Домашняя</Link>
      <Link to="/flows">🧾 Транзакции</Link>
      <Link to="/manage">🔧 Управление</Link>
      <Link to="/analysis">📊 Анализ</Link>
    </header>
  );
}
