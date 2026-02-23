import { logOut } from '@/helpers/Auth'
import style from '../../styles/Navbar.module.css'
import Link from 'next/link'
import { useState } from 'react'
import { Button } from '@mui/material'

export default function Navbar({
    onAddNewButtonClicked
}) {

    const [selectedOption, setSelectedOption] = useState(OPTIONS.INDEXED)

    return (
        <div className={`${style.wrapper}`}>
            <h1>Document Search</h1>
            <div className={style.options}>
                <Link
                    className={`${style.option} ${selectedOption === OPTIONS.INDEXED ? style.selectedOption : ''}`}
                    href={`/`}
                    onClick={() => setSelectedOption(OPTIONS.INDEXED)} >
                    Indexed Documents
                </Link>

                <Button
                    className={style.option}
                    disableRipple
                    onClick={() => onAddNewButtonClicked?.()} >
                    Add New
                </Button>

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