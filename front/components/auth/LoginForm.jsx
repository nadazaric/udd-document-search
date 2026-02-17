import { useState } from "react"
import style from '../../styles/Form.module.css'
import { Button } from "@mui/material"
import { LABEL } from "@/values/Labels"

export default function LoginForm({
    width = 250,
    onSubmitClick
}) {
    const [showPassword, setShowPassword] = useState(false)
    const [form, setForm] = useState({
        username: '',
        password: ''
    })

    function isButtonDisabled() {
        return form.username == '' || form.password == ''
    }

    return (
        <div style={{ width: width }}>
            <p className="big-title" style={{ textAlign: "center" }}>
                {LABEL.SIGN_IN}
            </p>

            <div className="spacer-h-m" />

            <div className={style.form}>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={form.username}
                        placeholder={LABEL.USERNAME}
                        onChange={(e) => { setForm({ ...form, username: e.target.value }) }} />
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.form}>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={form.password}
                        type={showPassword ? "text" : "password"}
                        placeholder={LABEL.PASSWORD}
                        onChange={(e) => { setForm({ ...form, password: e.target.value }) }} />

                    <span
                        onClick={() => setShowPassword(prevState => !prevState)}
                        className={`material-icons-outlined  ${style.inputIcon}`} >
                        {showPassword ? 'visibility_off' : 'visibility'}
                    </span>
                </div>
            </div>

            <div className="spacer-h-s" />

            <Button
                disableRipple
                className={`${style.button} ${style.raisedButton} w-full`}
                onClick={() => { if (onSubmitClick) onSubmitClick(form) }}
                disabled={isButtonDisabled()} >
                {LABEL.SIGN_IN}
            </Button>
        </div>
    )
}