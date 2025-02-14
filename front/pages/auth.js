import { useState } from 'react'
import style from '../styles/Auth.module.css'
import LoginForm from '@/components/auth/LoginForm'
import RegistrationForm from '@/components/auth/RgistrationForm'

export default function Auth() {
    const [isSwitch, setIsSwitch] = useState(true)
    const [isFirstRender, setIsFirstRender] = useState(true)
    const [isLogin, setIsLogin] = useState(true)

    function switchSids() {
        setIsSwitch(!isSwitch)
        setIsFirstRender(false)
        setTimeout(() => setIsLogin(!isLogin), 250)
        
    }

    function login(data) {
        console.log(data)
    }

    function register(data) {
        console.log(data)
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
        </div>
    )
}