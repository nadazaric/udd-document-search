import { KIBANA_VISUALISATION_URL } from "@/values/Enviroment"
import style from "../styles/Statistics.module.css"

export default function Statistics() {
  const kibanaUrl = KIBANA_VISUALISATION_URL

  return (
    <div className={style.page} >

      <div className={style.contentWrapper}>
        <iframe
          src={kibanaUrl}
          className={style.content}
          title="Kibana dashboard" />
      </div>
    </div>
  )
}