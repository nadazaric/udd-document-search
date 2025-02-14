import { useState } from 'react'
import style from '../styles/Auth.module.css'
import LoginForm from '@/components/auth/LoginForm'

export default function Auth() {
    const [isLogin, setIsLogin] = useState(true)
    const [isFirstRender, setIsFirstRender] = useState(true)

    function switchSids() {
        setIsLogin(!isLogin)
        setIsFirstRender(false)
    }

    function login(data) {
        console.log(data)
    }
    
    return(
        <div className={style.page}>
            <div className={`${style.grid} ${isFirstRender ? '' : isLogin ? style.notReversed : style.reversed}`}>
                    <div className={`${style.left}`}>
                        <LoginForm 
                            width={300}
                            onSubmitClick={(data) => login(data)}
                        />
                    </div>
                    <div className={`${style.right}`}>
                        <p>Right side</p>
                        <button onClick={switchSids}>Change</button>
                    </div>
                </div>
        </div>
    )
}