import { logOut } from '@/helpers/Auth'
import style from '../../styles/Navbar.module.css'
import { useRouter } from 'next/router'
import Link from 'next/link'
import { useEffect, useState } from 'react'

export default function Navbar() {
    const router = useRouter()
    const [selectedOption, setSelectedOption] = useState(OPTIONS.INDEXED)

    return(
        <div className={`${style.wrapper}`}>
            <h1>Document Search</h1>
            <div className={style.options}>
                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.INDEXED ? style.selectedOption : ''}`}
                    href={`/`}
                    onClick={() => setSelectedOption(OPTIONS.INDEXED)} >
                    Indexed Documents
                </Link>

                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.ADD ? style.selectedOption : ''}`}
                    href={`/`}
                    onClick={() => setSelectedOption(OPTIONS.ADD)} >
                    Add New
                </Link>
                
                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.REJECTED ? style.selectedOption : ''}`}
                    href={`/`}
                    onClick={() => setSelectedOption(OPTIONS.REJECTED)} >
                    Rejected Documents
                </Link>

                <Link
                    className={style.option}
                    href={`/auth`}
                    onClick={() => logOut()} >
                    Log out
                </Link>
            </div>
        </div>
    )
}

export const OPTIONS = {
    INDEXED: 'indexed',
    ADD: 'add',
    REJECTED: 'rejected'
}