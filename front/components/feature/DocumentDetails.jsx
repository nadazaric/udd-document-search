import style from "../../styles/DocumentInformation.module.css"
import { LABEL } from "@/values/Labels"
import IndexInfoCard from "./IndexInfoCard"

export default function DocumentDetails({ document }) {

    const behavior = document?.behaviorDescription ?? null

    function normalizeSnippet(html) {
        if (!html) return "";

        let s = html;

        s = s.replace(/\r?\n\s*\r?\n/g, "<br/><br/>");

        s = s.replace(/\r?\n/g, " ");

        s = s.replace(/[ \t]{2,}/g, " ").trim();

        return s;
    }

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
                    <div
                        className={style.paragraph}
                        dangerouslySetInnerHTML={{ __html: behavior }} />
                </div>
            }

            {document?.contentHighlights?.map((item) => (
                <div>
                    <div className={style.card}>
                        <div
                            className={style.highlightBox}
                            // dangerouslySetInnerHTML={{ __html: normalizeSnippet(item) }}
                            dangerouslySetInnerHTML={{ __html: item }} />
                    </div>

                    <div className="spacer-h-s" />
                </div>
            ))}

        </div>
    )
}