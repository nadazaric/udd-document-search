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
        setTimeout(() => setIsLogin(!isLogin), 350)
        
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

                    <div className={`${style.descriptionWrapper} ${style.signInDescription}`}>
                        <p className={`big-title`} style={{color: 'white'}}>Already Have an Account?</p>
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
                        <p className={`big-title`} style={{color: 'white'}}>New Here?</p>
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
            <Popup
                open={haveError}
                onClose={() => setHaveError(false)}
                message={errorMessage}
                severity="error"
            />
        </div>
    )
}