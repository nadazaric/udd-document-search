import Layout from "@/components/widgets/Layout"
import { getUserAccessToken } from "@/helpers/Auth"
import "@/styles/globals.css"
import axios from "axios"

axios.interceptors.request.use(
  (config) => {
    if (typeof window === "undefined") return config

    const token = getUserAccessToken()
    if (token && !config.headers?.skip) {
      config.headers.Authorization = `Bearer ${token.replace(/"/g, "")}`
    }
    return config
  },
  (error) => Promise.reject(error)
)


axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (getUserAccessToken() == null) {
      return Promise.reject(error);
    }

    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      if (!originalRequest._retry) {
        originalRequest._retry = true;
        logOut();
      }
    }

    return Promise.reject(error);
  }
);

export default function App({ Component, pageProps }) {
  return (
      <Layout>
          <Component {...pageProps} />
      </Layout>
  )
}