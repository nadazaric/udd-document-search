import style from '../../styles/Form.module.css'
import { useEffect, useState } from "react"
import Chips from '../widgets/Chips'

export default function AddNewDocumentForm({
    formData,
    onFormChange 
}) {

    const [form, setForm] = useState(formData ?? {})
    const CHIPS = [
        { value: "LOW", name: "Low" },
        { value: "MEDIUM", name: "Medium" },
        { value: "HIGH", name: "High" },
        { value: "CRITICAL", name: "Critical" },
    ]

    useEffect(() => {
        console.log(formData)
        setForm(formData)
    }, [formData])

    useEffect(() => {
        onFormChange?.(form)
    }, [form, onFormChange])

    function setField(key) {
        return (form?.[key] ?? "")
    }

    return (
        <div>
            <div className={style.twoColumns}>
                <div>
                    <div className={style.label}>Forensic Analyst Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("forensicAnalystName")}
                            onChange={(e) => {setForm({...form, forensicAnalystName: e.target.value})}} />
                    </div>
                </div>
                
                <div>
                    <div className={style.label}>CERT Organization Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("certOrganization")}
                            onChange={(e) => {setForm({...form, certOrganization: e.target.value})}} />
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
                            onChange={(e) => {setForm({...form, address: e.target.value})}} />
                    </div>
                </div>

                <div>
                    <div className={style.label}>Malware or Threat Name</div>
                    <div className={`${style.inputWrapper} w-full`}>
                        <input 
                            className={style.input}
                            value={setField("malwareOrThreatName")}
                            onChange={(e) => {setForm({...form, malwareOrThreatName: e.target.value})}} />
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
                            onChange={(e) => {setForm({...form, hash: e.target.value})}} />
                    </div>
                </div>

                <div>
                    <div className={style.label}>Clasification</div>
                    <Chips 
                        items={CHIPS}
                        value={formData.threatClassification ?? CHIPS[0].value}
                        onChange={(newValue) =>
                            setForm((prev) => ({ ...(prev ?? {}), threatClassification: newValue }))
                        }
                        allowDeselect={false} />
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.label}>Behavior Description</div>
                <div className={`${style.inputWrapper} ${style.textareaInputWrapper} w-full`}>
                <textarea 
                    className={style.input}
                    value={setField("behaviorDescription")}
                    onChange={(e) => {setForm({...form, behaviorDescription: e.target.value})}} />
            </div>
        </div>
    )
    
}