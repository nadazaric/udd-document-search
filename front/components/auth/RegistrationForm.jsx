import { Button } from "@mui/material"
import { useState } from "react"
import style from '../../styles/Form.module.css'
import { LABEL } from "@/values/Labels"

export default function RegistrationForm({
    width = 250,
    onSubmitClick,
}) {
    const [showPassword, setShowPassword] = useState(false)
    const [form, setForm] = useState({
        name: '',
        email: '',
        username: '',
        password: '',
    })

    function isButtonDisabled() {
        return form.username == '' || form.password == '' || form.name == '' || form.email == ''
    }

    return (
        <div style={{ width: width }}>
            <p className="big-title" style={{ textAlign: "center" }}>
                {LABEL.CREATE_ACCOUNT}
            </p>

            <div className="spacer-h-m" />

            <div className={style.form}>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={form.name}
                        placeholder={LABEL.FULL_NAME}
                        onChange={(e) => { setForm({ ...form, name: e.target.value }) }} />
                </div>
            </div>

            <div className="spacer-h-s" />

            <div className={style.form}>
                <div className={`${style.inputWrapper} w-full`}>
                    <input
                        className={style.input}
                        value={form.email}
                        placeholder={LABEL.EMAIL}
                        onChange={(e) => { setForm({ ...form, email: e.target.value }) }} />
                </div>
            </div>

            <div className="spacer-h-s" />

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
                onClick={() => onSubmitClick(form)}
                disabled={isButtonDisabled()} >
                {LABEL.SIGN_UP}
            </Button>
        </div>
    )
}