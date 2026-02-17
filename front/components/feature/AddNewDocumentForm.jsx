import style from '../../styles/Form.module.css'
import { useEffect, useRef, useState } from "react"
import Chips from '../widgets/Chips'

export default function AddNewDocumentForm({
    formData,
    onFormChange
}) {

    const [form, setForm] = useState(formData ?? {})
    const userEditRef = useRef(false)
    const CHIPS = [
        { value: "LOW", name: "Low" },
        { value: "MEDIUM", name: "Medium" },
        { value: "HIGH", name: "High" },
        { value: "CRITICAL", name: "Critical" },
    ]

    useEffect(() => {
        userEditRef.current = false
        setForm(formData)
    }, [formData])

    useEffect(() => {
        if (!userEditRef.current) return
        onFormChange?.(form)
        userEditRef.current = false
    }, [form, onFormChange])

    const updateForm = (patch) => {
        userEditRef.current = true
        setForm((prev) => ({ ...(prev ?? {}), ...patch }))
    }

    const setField = (key) => (form?.[key] ?? "")
    const setText = (key) => (e) => updateForm({ [key]: e?.target?.value ?? "" })
    const setValue = (key) => (val) => updateForm({ [key]: val })

    return (
        <div>
            <div className={style.twoColumns}>
                <div>
                    <div className={style.label}>Forensic Analyst Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("forensicAnalystName")}
                            onChange={setText("forensicAnalystName")} />
                    </div>
                </div>
                
                <div>
                    <div className={style.label}>CERT Organization Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("certOrganization")}
                            onChange={setText("certOrganization")} />
                    </div>
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.twoColumns}>
                <div>
                    <div className={style.label}>Address</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("address")}
                            onChange={setText("address")} />
                    </div>
                </div>

                <div>
                    <div className={style.label}>Malware or Threat Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("malwareOrThreatName")}
                            onChange={setText("malwareOrThreatName")} />
                    </div>
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.twoColumns}>
                <div>
                    <div className={style.label}>Hash Value</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("hash")}
                            onChange={setText("hash")} />
                    </div>
                </div>

                <div>
                    <div className={style.label}>Clasification</div>
                    <Chips 
                        items={CHIPS}
                        value={formData.threatClassification ?? CHIPS[0].value}
                        onChange={setValue("threatClassification")}
                        allowDeselect={false} />
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.label}>Behavior Description</div>
                <div className={`${style.inputWrapper} ${style.textareaInputWrapper} w-full`}>
                <textarea 
                    className={style.input}
                    value={setField("behaviorDescription")}
                    onChange={setText("behaviorDescription")} />
            </div>
        </div>
    )

}