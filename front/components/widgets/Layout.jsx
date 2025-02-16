import style from '../../styles/Layout.module.css'
import { useEffect, useState } from "react"
import Navbar from './Navbar'
import { useRouter } from 'next/router'
import { getUserRole } from '@/helpers/Auth'

export default function Layout({ children }) {
    const router = useRouter()
    const [isPageVisible, setIsPageVisible] = useState(false) 
    const [isNavbarVisible, setIsNavbarVisible] = useState(false)

    useEffect(() => {
        const role = getUserRole()
        if (!router.pathname.includes('auth') && !role) {
            setIsPageVisible(false)
            router.push('/auth')
        } else setIsPageVisible(true)
        if (router.pathname.includes('auth')) setIsNavbarVisible(false)
        else setIsNavbarVisible(true)
    }, [router])

    if (!isPageVisible) return null
    else return (
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