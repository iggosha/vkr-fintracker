import { Routes, Route } from "react-router-dom";
import { MoneyFlowsPage } from "./pages/MoneyFlowsPage";
import { AnalysisPage } from "./pages/AnalysisPage";
// import { ManagingPage } from "./pages/ManagingPage";
import { Layout } from "./components/common/Layout";
import "./styles/common/app.css"

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="flows" element={<MoneyFlowsPage />} />
        <Route path="analysis" element={<AnalysisPage />} />
        {/* <Route path="manage" element={<ManagingPage />} /> */}
      </Route>
    </Routes>
  );
}

export default App;
