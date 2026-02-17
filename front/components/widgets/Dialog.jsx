import { useEffect, useRef } from "react"
import style from '../../styles/Dialog.module.css'
import CloseIcon from '@mui/icons-material/Close'
import { Button } from "@mui/material"

export function Dialog({
    isOpen = false,
    width = 300,
    onCloseModal,
    children
}) {
    const ref = useRef()
    useEffect(() => {
        if (isOpen) ref.current?.showModal()
        else {
            setTimeout(() => {
                ref.current?.close();
            }, 300);
        }
    }, [isOpen])

    return (
        <dialog
            className={`${style.dialog} ${isOpen ? style.show : style.hide}`}
            style={{ width: `${width}px` }}
            ref={ref}
            onCancel={onCloseModal} >
            {children}
        </dialog>
    )
}

export function DialogWithHeader({
    isOpen = false,
    width = 300,
    onCloseModal,
    title,
    children,
    haveError,
    errorDescription
}) {
    return (
        <Dialog
            isOpen={isOpen}
            width={width}
            onCloseModal={onCloseModal} >
            <div className={style.header}>
                <div className='title' >{title}</div>
                <CloseIcon
                    className='icon'
                    onClick={() => { if (onCloseModal) onCloseModal() }} />
            </div>
            {haveError &&
                <div>
                    {/* <div className='spacer_hor_S'/> */}
                    <div className="error">{errorDescription}</div>
                </div>
            }
            <div className='spacer_hor_M' />
            <div>
                {children}
            </div>
        </Dialog>
    )
}

export function DialogConfirme({
    isOpen = false,
    width = 300,
    neutralActionText,
    onNeutralAction,
    actionText,
    onAction,
    title,
    description
}) {

    return (
        <Dialog
            isOpen={isOpen}
            width={width}
            onCloseModal={onNeutralAction} >

            <div className={style.header}>
                <div className='title' >{title}</div>
            </div>

            <div className='spacer_hor_S' />
            {description}
            <div className='spacer_hor_S' />

            <div className={style.buttonWrapper}>
                <Button
                    onClick={(e) => {
                        e.preventDefault()
                        if (onNeutralAction) onNeutralAction()
                    }}
                    className="outlinedButton" >
                    {neutralActionText}
                </Button>

                <Button
                    onClick={(e) => {
                        e.stopPropagation()
                        if (onAction) onAction()
                    }}
                    className="raisedButton" >
                    {actionText}
                </Button>
            </div>

        </Dialog>
    )
}