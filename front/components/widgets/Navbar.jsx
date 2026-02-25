import { logOut } from '@/helpers/Auth'
import style from '../../styles/Navbar.module.css'
import Link from 'next/link'
import { useEffect, useState } from 'react'
import { Button } from '@mui/material'
import { useRouter } from 'next/router'

export default function Navbar({
    onAddNewButtonClicked
}) {
    const router = useRouter()
    const [selectedOption, setSelectedOption] = useState(OPTIONS.INDEXED)

    useEffect(() => {
        if (router.pathname.split('/')[1] == OPTIONS.STATISTICS) setSelectedOption(OPTIONS.STATISTICS)
    }, [router])

    return (
        <div className={`${style.wrapper}`}>
            <h1>Document Search</h1>
            <div className={style.options}>
                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.INDEXED ? style.selectedOption : ''}`}
                    href={`/`}
                    onClick={() => setSelectedOption(OPTIONS.INDEXED)} >
                    Documents
                </Link>

                <Button
                    className={style.option}
                    disableRipple
                    onClick={() => onAddNewButtonClicked?.()} >
                    Add New
                </Button>

                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.STATISTICS ? style.selectedOption : ''}`}
                    href={`/statistics`}
                    onClick={() => setSelectedOption(OPTIONS.STATISTICS)} >
                    Statistics
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
    STATISTICS: 'statistics'
}