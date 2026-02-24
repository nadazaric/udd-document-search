import style from "../../styles/DocumentInformation.module.css"
import { LABEL } from "@/values/Labels"
import IndexInfoCard from "./IndexInfoCard"

export default function DocumentDetails({ document }) {

    const behavior = document?.behaviorDescription ?? null

    return (
        <div className={style.wrapper}>
            <IndexInfoCard
                item={document}
                isClickable={false} />

            <div className="spacer-h-s" />

            {behavior &&
                <div className={style.card}>
                    <div className={style.title}>{LABEL.BEHAVIOR_DESCRIPTION}</div>
                    <div className="spacer-h-s" />
                    <div className={style.paragraph}>{behavior}</div>
                </div>
            }

        </div>
    )
}