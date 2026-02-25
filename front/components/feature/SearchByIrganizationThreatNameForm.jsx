import { useEffect, useMemo, useState } from "react";
import style from '../../styles/Form.module.css'
import searchStyle from '../../styles/Search.module.css'
import { LABEL } from "@/values/Labels";
import { Button } from "@mui/material";

export default function SearchByOrganizationThreatNameForm({
    isOpen,
    onSubmit
}) {
    const [form, setForm] = useState({ certOrganization: '', malwareOrThreatName: '' });

    useEffect(() => {
        if (!isOpen) {
            setForm({ certOrganization: '', malwareOrThreatName: '' })
        }
    }, [isOpen])

    // Validations

    const isNonEmpty = (x) => String(x ?? "").trim().length > 0

    const isFormValid = useMemo(() => {
        if (!form) return false
        return (
            isNonEmpty(form.certOrganization) &&
            isNonEmpty(form.malwareOrThreatName) 
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
            certOrganization: form.certOrganization.trim(),
            malwareOrThreatName: form.malwareOrThreatName.trim()
        })
    }

    return (
        <div className={searchStyle.rightWrapper}>
            <div>
                <div className={style.label}>{LABEL.ORGANIZATION}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={setField("certOrganization")}
                        onChange={setText("certOrganization")} />
                </div>

                <div className="spacer-h-s" />

                <div className={style.label}>{LABEL.MALWARE_NAME}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={setField("malwareOrThreatName")}
                        onChange={setText("malwareOrThreatName")} />
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