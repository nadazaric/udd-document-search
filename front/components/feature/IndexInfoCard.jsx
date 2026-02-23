import { LABEL } from "@/values/Labels"
import style from "../../styles/IndexInfoCard.module.css"

export default function IndexInfoCard({ item, onClick }) {
    if (!item) return null

    const analyst = item.forensicAnalystName ?? "-"
    const hash = item.hash ?? "-"
    const classification = item.threatClassification ?? "-"
    const org = item.certOrganization ?? "-"
    const threat = item.malwareOrThreatName ?? "-"

    return (
        <div
            className={style.card}
            onClick={() => { if (onClick) onClick(item) }} >
            <div className={style.header}>
                <div
                    className={style.title}
                    dangerouslySetInnerHTML={{ __html: analyst }} />

                <div
                    className={`${style.badge} ${style[`badge_${String(classification).toLowerCase()}`]}`}>
                    {classification}
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.row}>
                <div className={style.label}>{LABEL.ORGANIZATION}</div>
                <div className={style.value} dangerouslySetInnerHTML={{ __html: org }} />
            </div>

            <div className={style.row}>
                <div className={style.label}>{LABEL.MALWARE_NAME}</div>
                <div className={style.value} dangerouslySetInnerHTML={{ __html: threat }} />
            </div>

            <div className={style.row}>
                <div className={style.label}>{LABEL.HASH}</div>
                <div className={style.mono}>{hash}</div>
            </div>
        </div>
    )
}