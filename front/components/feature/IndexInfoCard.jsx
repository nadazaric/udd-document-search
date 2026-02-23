import style from "../../styles/IndexInfoCard.module.css"

export default function IndexInfoCard({ item, onClick }) {
    if (!item) return null

    const analyst = item.forensicAnalystName ?? "-"
    const hash = item.hash ?? "-"
    const classification = item.threatClassification ?? "-"
    const org = item.certOrganization ?? "-"
    const threat = item.malwareOrThreatName ?? "-"

    return (
        <div className={style.card} onClick={() => { if (onClick) onClick(item) }} role="button" tabIndex={0} onKeyDown={(e) => { if (e.key === "Enter" || e.key === " ") { if (onClick) onClick(item) } }}>
            <div className={style.header}>
                <div className={style.title} dangerouslySetInnerHTML={{ __html: analyst }} />
                <div className={`${style.badge} ${style[`badge_${String(classification).toLowerCase()}`]}`}>{classification}</div>
            </div>

            <div className={style.row}>
                <div className={style.label}>Organization</div>
                <div className={style.value} dangerouslySetInnerHTML={{ __html: org }} />
            </div>

            <div className={style.row}>
                <div className={style.label}>Threat</div>
                <div className={style.value} dangerouslySetInnerHTML={{ __html: threat }} />
            </div>

            <div className={style.row}>
                <div className={style.label}>Hash</div>
                <div className={style.mono}>{hash}</div>
            </div>
        </div>
    )
}