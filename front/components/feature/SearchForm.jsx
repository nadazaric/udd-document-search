import { useEffect, useState } from "react"
import { OptionsPanel } from "../widgets/OptionsPanel"
import style from '../../styles/Search.module.css'
import SearchByAnalystHashClassificationForm from "./SearchByAnalystHashClassificationForm"
import { SEARCH_PAGE } from "@/helpers/Enums"
import { LABEL } from "@/values/Labels"
import SearchByOrganizationThreatNameForm from "./SearchByIrganizationThreatNameForm"
import SearchKnnForm from "./SearchKnnForm"
import SearchFullTextForm from "./SearchFullTextForm"
import SearchCombinedBooleanForm from "./SearchCombinedBooleanForm"

export default function SearchForm({
    isOpen,
    onSubmit
}) {
    const options = [
        {
            key: SEARCH_PAGE.ANALYST_HASH_CLASS,
            label: LABEL.ANALYST_HASH_CLASS_TITLE,
            hint: LABEL.ANALYST_HASH_CLASS_HINT
        },
        {
            key: SEARCH_PAGE.ORG_THREAT,
            label: LABEL.ORG_THREAT_TITLE,
            hint: LABEL.ORG_THREAT_HINT
        },
        {
            key: SEARCH_PAGE.KNN_FREE_TEXT,
            label: LABEL.KNN_FREE_TEXT_TITLE,
            hint: LABEL.KNN_FREE_TEXT_HINT
        },
        {
            key: SEARCH_PAGE.FULLTEXT_PDF,
            label: LABEL.FULLTEXT_PDF_TITLE,
            hint: LABEL.FULLTEXT_PDF_HINT
        },
        {
            key: SEARCH_PAGE.BOOLEAN_SEMI_STRUCTURED,
            label: LABEL.BOOLEAN_SEMI_STRUCTURED_TITLE,
            hint: LABEL.BOOLEAN_SEMI_STRUCTURED_HINT
        }
    ]

    const [selectedKey, setSelectedKey] = useState(options[0].key)

    useEffect(() => {
        if (!isOpen) {
            setSelectedKey(SEARCH_PAGE.ANALYST_HASH_CLASS)
        }
    }, [isOpen])

    return (
        <div className={style.searchWrapper}>
            <OptionsPanel
                options={options}
                selectedKey={selectedKey}
                onSelect={setSelectedKey} />

            <div className={style.divider} />

            {selectedKey === SEARCH_PAGE.ANALYST_HASH_CLASS &&
                <SearchByAnalystHashClassificationForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }

            {selectedKey === SEARCH_PAGE.ORG_THREAT &&
                <SearchByOrganizationThreatNameForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }

            {selectedKey === SEARCH_PAGE.KNN_FREE_TEXT &&
                <SearchKnnForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }

            {selectedKey === SEARCH_PAGE.FULLTEXT_PDF &&
                <SearchFullTextForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }

            {selectedKey === SEARCH_PAGE.BOOLEAN_SEMI_STRUCTURED &&
                <SearchCombinedBooleanForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }
        </div>
    )
}