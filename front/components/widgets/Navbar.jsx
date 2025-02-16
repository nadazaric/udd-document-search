import { logOut } from '@/helpers/Auth'
import style from '../../styles/Navbar.module.css'
import { useRouter } from 'next/router'

export default function Navbar() {
    const router = useRouter()

    return(
        <div className={`${style.wrapper}`}>
            <h1>Title</h1>
            <button onClick={() => {
                router.push('/auth')
                logOut()
            }}>
                Log out
            </button>
        </div>
    )
}