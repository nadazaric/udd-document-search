import { LABEL } from '@/values/Labels'
import style from '../../styles/Form.module.css'
import { useEffect, useMemo, useState } from 'react'
import Chips from '../widgets/Chips'
import { Button } from '@mui/material'
import searchStyle from '../../styles/Search.module.css'
import axios from 'axios'
import { BACK_BASE_URL } from '@/values/Enviroment'

export default function SearchByAnalystHashClassificationForm({
    isOpen, 
    onSearcheDone
}) {

    const [form, setForm] = useState({
        forensicAnalystName: '',
        hash: '',
        threatClassification: 'LOW'
    });

    useEffect(() => {
        if (!isOpen) {
            setForm({ forensicAnalystName: "", hash: "", threatClassification: "LOW" })
            // setErrors({})
        }
    }, [isOpen])

    const CHIPS = [
        { value: "LOW", name: LABEL.LOW_CLASSIFICATION },
        { value: "MEDIUM", name: LABEL.MEDIUM_CLASSIFICATION },
        { value: "HIGH", name: LABEL.HIGH_CLASSIFICATION },
        { value: "CRITICAL", name: LABEL.CRITICAL_CLASSIFICATION },
    ]

    const isNonEmpty = (x) => String(x ?? "").trim().length > 0
    const isFormValid = useMemo(() => {
        if (!form) return false
        return (
            isNonEmpty(form.forensicAnalystName) &&
            isNonEmpty(form.hash)
        )
    }, [form])

    const updateForm = (patch) => {
        setForm((prev) => ({ ...(prev ?? {}), ...patch }))
    }

    const setField = (key) => (form?.[key] ?? "")
    const setText = (key) => (e) => updateForm({ [key]: e?.target?.value ?? "" })
    const setValue = (key) => (val) => updateForm({ [key]: val })

    async function search() {
        try {
            const response = await axios.post(`${BACK_BASE_URL}/search/by-analyst-hash-classification?page=0&size=5`, form)
            onSearcheDone?.(response)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div className={searchStyle.rightWrapper}>
            <div>
                <div className={style.label}>{LABEL.FORENSIC_NAME}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={setField("forensicAnalystName")}
                        onChange={setText("forensicAnalystName")} />
                </div>

                <div className="spacer-h-s" />

                <div className={style.label}>{LABEL.HASH}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={setField("hash")}
                        onChange={setText("hash")} />
                </div>

                <div className="spacer-h-s" />

                <div className={style.label}>{LABEL.CLASSIFICATION}</div>
                <Chips
                    items={CHIPS}
                    value={form.threatClassification === '' ? CHIPS[0].value : form.threatClassification}
                    onChange={setValue("threatClassification")}
                    allowDeselect={false} />
            </div>

            <div className={style.buttonsWrapperToRight}>
                <Button
                    className={`${style.button} ${style.raisedButton}`}
                    disableRipple
                    disabled={!isFormValid}
                    onClick={() => search()} >
                    {LABEL.SEARCH}
                </Button>
            </div>
        </div>
    )
}