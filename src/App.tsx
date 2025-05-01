import { Routes, Route } from "react-router-dom";
import { MoneyFlowsPage } from "./pages/MoneyFlowsPage";
import { Layout } from "./components/Layout";
import "./styles/app.css"

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="flows" element={<MoneyFlowsPage />} />
        {/* <Route path="analyze" element={<AnalysisPage />} /> */}
        {/* <Route path="manage" element={<ManagePage />} /> */}
      </Route>
    </Routes>
  );
}

export default App;
