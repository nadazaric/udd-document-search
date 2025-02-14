import { useState } from 'react'
import style from '../styles/Auth.module.css'
import formStyle from '../styles/Form.module.css'
import LoginForm from '@/components/auth/LoginForm'
import RegistrationForm from '@/components/auth/RegistrationForm'
import { Popup } from '@/components/widgets/Popup'
import { isValidEmail } from '@/helpers/RepresentationHelpers'
import { ERROR } from '@/values/Errors'
import { Button } from '@mui/material'

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
                    <div className={`${style.descriptionWrapper}`}>
                        <p className={`big-title`} style={{color: 'white'}}>Right side</p>
                        <div className="spacer-h-m" />
                        <p className={style.description}>Some description. Lorem ipsum dolorem, bla bla bla.</p>
                        <div className="spacer-h-s" />
                        <Button 
                            disableRipple 
                            className={`${formStyle.button} ${formStyle.reversedOutlinedButton}`}
                            onClick={() => switchSids()}
                        >
                            Neko dugme
                        </Button>
                    </div>
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