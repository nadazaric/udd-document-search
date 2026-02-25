import { LABEL } from "@/values/Labels"
import style from "../../styles/DocumentInformation.module.css"

export default function IndexInfoCard({
    item,
    isClickable,
    onClick
}) {
    if (!item) return null

    const analyst = item.forensicAnalystName ?? "-"
    const hash = item.hash ?? "-"
    const address = item.address ?? '-'
    const classification = item.threatClassification ?? "-"
    const org = item.certOrganization ?? "-"
    const threat = item.malwareOrThreatName ?? "-"
    const score = item.score ? parseFloat(item.score).toFixed(2) : null

    return (
        <div
            className={`${style.card} ${isClickable ? style.clickableCard : ''}`}
            onClick={(e) => { if (isClickable) onClick(item) }} >
            <div className={style.header}>
                <div
                    className={style.title}
                    dangerouslySetInnerHTML={{ __html: analyst }} />

                <div className={style.badgesWrapper}>
                    {score &&
                        <div
                            className={`${style.badge} ${style.badgeNeutral}`}>
                            {score}
                        </div>
                    }
                    <div
                        className={`${style.badge} ${style[`badge_${String(classification).toLowerCase()}`]}`}>
                        {classification}
                    </div>
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.row}>
                <div className={style.label}>{LABEL.ORGANIZATION}</div>
                <div className={style.value} dangerouslySetInnerHTML={{ __html: org }} />
            </div>

            <div className={style.row}>
                <div className={style.label}>{LABEL.ADDRESS}</div>
                <div className={style.value} >{address}</div>
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