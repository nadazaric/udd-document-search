import { useState } from 'react'
import style from '../styles/Auth.module.css'
import formStyle from '../styles/Form.module.css'
import LoginForm from '@/components/auth/LoginForm'
import { isValidEmail } from '@/helpers/RepresentationHelpers'
import { ERROR, SUCCESS } from '@/values/Messages'
import { Button } from '@mui/material'
import axios from 'axios'
import { BACK_BASE_URL } from '@/values/Enviroment'
import { putUserAccessToken } from '@/helpers/Auth'
import { useRouter } from 'next/router'
import { SEVERITY } from '@/helpers/Enums'
import { usePopup } from '@/components/widgets/PopupProvider'
import RegistrationForm from '@/components/auth/RegistrationForm'

export default function Auth() {
    const [isSwitch, setIsSwitch] = useState(true)
    const [isFirstRender, setIsFirstRender] = useState(true)
    const [isLogin, setIsLogin] = useState(true)
    const router = useRouter()
    const { showPopup } = usePopup()

    function switchSids() {
        setIsSwitch(!isSwitch)
        setIsFirstRender(false)
        setTimeout(() => setIsLogin(!isLogin), 350)
    }

    async function login(data) {
        try {
            const response = await axios.post(`${BACK_BASE_URL}/user/login`, data, { headers: { 'skip': true } })
            if (response.status === 200) {
                putUserAccessToken(response.data.accessToken)
                router.push('/')
            }
        } catch (error) {
            showPopup({
                message: error.status == 401 ? ERROR.LOGIN_WRONG_CREDENTIALS : ERROR.SERVER,
                severity: SEVERITY.ERROR
            })
        }
    }

    async function register(data) {
        if (!checkSingUpData(data)) return
        try {
            const response = await axios.post(`${BACK_BASE_URL}/user/register`, data, { headers: { 'skip': true } })
            if (response.status === 201) {
                switchSids()
                showPopup({
                    message: SUCCESS.REGISTRATION_DONE,
                    severity: SEVERITY.SUCCESS
                })
            }
        } catch (error) {
            showPopup({
                message: error.status == 401 ? ERROR.REGISTRATION_USERNAME_EXIST : ERROR.SERVER,
                severity: SEVERITY.ERROR
            })
        }
    }

    function checkSingUpData(data) {
        // check email pattern
        if (!isValidEmail(data.email)) {
            showPopup({
                message: ERROR.REGISTRATION_EMAIL,
                severity: SEVERITY.ERROR
            })
            return false
        }
        return true
    }

    return (
        <div className={style.page}>
            <div className={`${style.grid} ${isFirstRender ? '' : isSwitch ? style.notReversed : style.reversed}`}>
                <div className={`${style.formSide}`}>
                    {isLogin ?
                        <LoginForm
                            width={300}
                            onSubmitClick={(data) => login(data)}
                        /> :
                        <RegistrationForm
                            width={300}
                            onSubmitClick={(data) => register(data)}
                        />
                    }
                </div>
                <div className={`${style.descriptionSide}`}>

                    <div className={`${style.descriptionWrapper} ${style.signInDescription}`}>
                        <p className={`big-title`} style={{ color: 'white' }}>Already Have an Account?</p>
                        <div className="spacer-h-m" />
                        <p className={style.description}>Welcome back! Log in to access your account.</p>
                        <div className="spacer-h-m" />
                        <Button
                            disableRipple
                            className={`${formStyle.button} ${formStyle.reversedOutlinedButton}`}
                            onClick={() => switchSids()}
                        >
                            Sign In
                        </Button>
                    </div>

                    <div className={`${style.descriptionWrapper} ${style.signUpDescription}`}>
                        <p className={`big-title`} style={{ color: 'white' }}>New Here?</p>
                        <div className="spacer-h-m" />
                        <p className={style.description}>Create an account to get started! Sign up now and enjoy all the features.</p>
                        <div className="spacer-h-m" />
                        <Button
                            disableRipple
                            className={`${formStyle.button} ${formStyle.reversedOutlinedButton}`}
                            onClick={() => switchSids()}
                        >
                            Sign Up
                        </Button>
                    </div>

                </div>
            </div>
        </div>
    )
}