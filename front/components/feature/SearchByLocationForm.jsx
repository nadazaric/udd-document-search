import { useEffect, useMemo, useState } from "react";
import style from '../../styles/Form.module.css'
import searchStyle from '../../styles/Search.module.css'
import { LABEL } from "@/values/Labels";
import { Button } from "@mui/material";
import { ERROR } from "@/values/Messages";

export default function SearchByLocationForm({
    isOpen,
    onSubmit
}) {
    const [form, setForm] = useState({ address: '', distance: null });

    useEffect(() => {
        if (!isOpen) {
            setForm({ address: '', distance: null })
        }
    }, [isOpen])

    // Validations
    const [errors, setErrors] = useState({ distance: "" })

    const validateDistance = (v) => {
        if (v === null || v === undefined || String(v).trim() === "") return ""
        const n = Number(v)
        if (!Number.isFinite(n)) return ERROR.DISTANCE_NOT_NUBER
        if (n < 1) return ERROR.DISTANCE_NOT_ZERO
        return ""
    }

    const isNonEmpty = (x) => String(x ?? "").trim().length > 0

    const isFormValid = useMemo(() => {
        if (!form) return false
        return (
            isNonEmpty(form.address) &&
            isNonEmpty(form.distance) &&
            !validateDistance(form.distance)
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
            address: form.address.trim(),
            distance: Number(form.distance)
        })
    }

    return (
        <div className={searchStyle.rightWrapper}>
            <div>
                <div className={style.label}>{LABEL.ADDRESS}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={setField("address")}
                        onChange={setText("address")} />
                </div>

                <div className="spacer-h-s" />

                <div className={style.label}>{LABEL.DISTANCE}</div>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        type="number"
                        step={10}
                        className={style.input}
                        value={setField("distance")}
                        onChange={(e) => {
                            const val = e?.target?.value ?? ""
                            updateForm({ distance: val })
                            setErrors((prev) => ({ ...(prev ?? {}), distance: validateDistance(val) }))
                        }} />
                </div>
                {errors.distance ? <div className={style.inputError}>{errors.distance}</div> : null}
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