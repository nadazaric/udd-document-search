import { useEffect, useState } from "react"
import { OptionsPanel } from "../widgets/OptionsPanel"
import style from '../../styles/Search.module.css'
import SearchByAnalystHashClassificationForm from "./SearchByAnalystHashClassificationForm"

export default function SearchForm({
    isOpen,
    onSubmit
}) {
    const options = [
        {
            key: "ANALYST_HASH_CLASS",
            label: "Analyst, Hash & Classification",
            hint: "Find reports by forensic analyst, hash value, and threat classification."
        },
        {
            key: "ORG_THREAT",
            label: "Organization & Threat Name",
            hint: "Search by CERT/CSIRT organization and analyzed malware/threat name."
        },
        {
            key: "KNN_FREE_TEXT",
            label: "Semantic Search (KNN)",
            hint: "Approximate nearest-neighbor search using a free-text query."
        },
        {
            key: "FULLTEXT_PDF",
            label: "Report Description (Full-text)",
            hint: "Search within the report text extracted from the PDF."
        },
        {
            key: "BOOLEAN_SEMI_STRUCTURED",
            label: "Advanced Boolean Search",
            hint: "Combine conditions with AND / OR / NOT, with C-like operator precedence."
        }
    ]

    const [selectedKey, setSelectedKey] = useState(options[0].key)

    useEffect(() => {
        if (!isOpen) {
            setSelectedKey("ANALYST_HASH_CLASS")
        }
    }, [isOpen])

    return (
        <div className={style.searchWrapper}>
            <OptionsPanel
                options={options}
                selectedKey={selectedKey}
                onSelect={setSelectedKey} />

            <div className={style.divider} />

            {selectedKey === "ANALYST_HASH_CLASS" &&
                <SearchByAnalystHashClassificationForm
                    isOpen={isOpen}
                    onSubmit={(payload) => onSubmit?.(selectedKey, payload)} />
            }
        </div>
    )
}