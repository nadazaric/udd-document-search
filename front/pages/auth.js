import { useState } from 'react'
import style from '../styles/Auth.module.css'
import LoginForm from '@/components/auth/LoginForm'
import RegistrationForm from '@/components/auth/RgistrationForm'
import { Popup } from '@/components/widgets/Popup'
import { isValidEmail } from '@/helpers/RepresentationHelpers'
import { ERROR } from '@/values/Errors'

export default function Auth() {
    const [isSwitch, setIsSwitch] = useState(true)
    const [isFirstRender, setIsFirstRender] = useState(true)
    const [isLogin, setIsLogin] = useState(true)
    const [haveError, setHaveError] = useState(false)
    const [errorMessage, setErrorMesagge] = useState('')

    function switchSids() {
        setIsSwitch(!isSwitch)
        setIsFirstRender(false)
        setTimeout(() => setIsLogin(!isLogin), 250)
        
    }

    function login(data) {
        console.log(data)
    }

    function register(data) {
        if(checkSingUpData(data)) {
            // call back
            switchSids()
            console.log(data)
        }
    }

    function checkSingUpData(data) {
        // check email pattern
        if (!isValidEmail(data.email)) {
          setHaveError(true)
          setErrorMesagge(ERROR.REGISTRATION_EMAIL)
          return false
        }
        return true
    }
    
    return(
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
                    <p>Right side</p>
                    <button onClick={switchSids}>Change</button>
                </div>
            </div>
            <Popup
                open={haveError}
                onClose={() => setHaveError(false)}
                message={errorMessage}
                severity="error"
            />
        </div>
    )
}