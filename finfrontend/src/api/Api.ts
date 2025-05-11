import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.API_URL || "http://localhost:8080/api/v1",
});

export default api;