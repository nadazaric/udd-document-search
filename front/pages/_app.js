import Layout from "@/components/widgets/Layout"
import { getUserAccessToken, getUserRefreshToken } from "@/helpers/Auth"
import "@/styles/globals.css"
import { BACK_BASE_URL } from "@/values/Enviroment"
import axios from "axios"

axios.interceptors.request.use(
  config => {
      const token = getUserAccessToken()
      if (token && !config.headers['skip']) config.headers['Authorization'] = `Bearer  ${token.replace(/"/g, '')}` 
      return config
  },
  error => {
      Promise.reject(error)
  }
)

axios.interceptors.response.use(
  response => { return response },
  async function (error) {
    const originalRequest = error.config
    if (getUserAccessToken() == null) return
    if (error.response && error.response.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true
        return axios.post(`${BACK_BASE_URL}/api/user/refresh`, {
            'accessToken': getUserAccessToken(),
            'refreshToken': getUserRefreshToken(),
          }, {
            headers: {
                'Content-Type': 'application/json',
                'skip': true
            },
          }).then(response => {
            if (response.status === 200) {
                localStorage.setItem('accessToken', response.data['accessToken'])
                localStorage.setItem('refreshToken', response.data['refreshToken'])
                return axios(originalRequest)
            } else {
                logOut();
            }
          }).catch(_err => {
            logOut()
          })
    }
    return Promise.reject(error)
  }
)

export default function App({ Component, pageProps }) {
  return (
      <Layout>
          <Component {...pageProps} />
      </Layout>
  )
}