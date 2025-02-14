import { useEffect, useState } from 'react'
import style from '../styles/Auth.module.css'

export default function Auth() {
    const [isLogin, setIsLogin] = useState(true)
    const [isFirstRender, setIsFirstRender] = useState(true)

    function switchSids() {
        setIsLogin(!isLogin)
        setIsFirstRender(false)
    }

    return(
        <div className={style.page}>
            <div
                className={`${style.grid} ${isFirstRender ? '' : isLogin ? style.notReversed : style.reversed}`}
            >
                    <div className={`${style.left}`}>
                        <p>{isLogin ? 'Login' : 'Registration'}</p>
                        <p>Left side</p>
                    </div>
                    <div className={`${style.right}`}>
                        <p>Right side</p>
                        <button onClick={switchSids}>Change</button>
                    </div>
                </div>
        </div>
    )
}