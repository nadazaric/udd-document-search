import { useEffect, useMemo, useState } from "react";
import style from '../../styles/Form.module.css'
import searchStyle from '../../styles/Search.module.css'
import { LABEL } from "@/values/Labels";
import { Button } from "@mui/material";

export default function SearchKnnForm({
    isOpen,
    onSubmit
}) {
    const [form, setForm] = useState({ text: '' });

    useEffect(() => {
        if (!isOpen) {
            setForm({ text: '' })
        }
    }, [isOpen])

    // Validations

    const isNonEmpty = (x) => String(x ?? "").trim().length > 0

    const isFormValid = useMemo(() => {
        if (!form) return false
        return (
            isNonEmpty(form.text)
        )
    }, [form])

    // Setters 

    const updateForm = (patch) => {
        setForm((prev) => ({ ...(prev ?? {}), ...patch }))
    }
    const setField = (key) => (form?.[key] ?? "")
    const setText = (key) => (e) => updateForm({ [key]: e?.target?.value ?? "" })

    function submit() {
        if (!isFormValid) return
        onSubmit?.({
            text: form.text.trim()
        })
    }

    return (
        <div className={searchStyle.rightWrapper}>
            <div>
                <div className={style.label}>{LABEL.TEXT}</div>
                <div className={`${style.inputWrapper} ${style.textareaInputWrapper} w-full`}>
                    <textarea
                        className={style.input}
                        value={setField("text")}
                        onChange={setText("text")} />
                </div>
            </div>

            <div className={style.buttonsWrapperToRight}>
                <Button
                    className={`${style.button} ${style.raisedButton}`}
                    disableRipple
                    disabled={!isFormValid}
                    onClick={() => submit()} >
                    {LABEL.SEARCH}
                </Button>
            </div>
        </div>
    )
}