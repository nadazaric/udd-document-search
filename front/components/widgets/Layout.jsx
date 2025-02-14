import style from '../../styles/Layout.module.css'
import { useEffect, useState } from "react"
import Navbar from './Navbar'

export default function Layout({ children }) {

    const [isNavbarVisible, setIsNavbarVisible] = useState(false)
    useEffect(() => {
        if (typeof window !== "undefined") {
            setIsNavbarVisible(!window.location.pathname.includes("auth"))
        }
    }, [])

    return (
        <div>
            {isNavbarVisible && 
                <div className={style.navbar}> <Navbar/> </div>
            }
            <div className={`${style.content} ${!isNavbarVisible ? style.contentWhenHiddenNavbar : ''}`}>
                {children}
            </div>
        </div>
    )
}