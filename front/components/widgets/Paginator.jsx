import { Button } from "@mui/material"
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft"
import ChevronRightIcon from "@mui/icons-material/ChevronRight"
import style from "../../styles/Paginator.module.css"
import formStyle from "../../styles/Form.module.css"

export default function Paginator({
    page,
    onPageChange,
    additionalActionName,
    onAdditionalActionClick
}) {
    if (!page) return null

    const current = (page.number ?? 0) + 1
    const totalPages = page.totalPages ?? 0
    const totalElements = page.totalElements ?? 0

    return (
        <div className={style.wrapper}>
            <div className={style.info}>
                <div className={style.pageText}>Page <span className={style.strong}>{current}</span> of <span className={style.strong}>{totalPages}</span></div>
                <div className={style.subText}>{totalElements} total results</div>
            </div>

            <div className={style.actions}>
                <Button
                    className={`${formStyle.button} ${formStyle.outlinedButton}`}
                    disableRipple
                    disabled={!!page.first}
                    onClick={() => onPageChange((page.number ?? 0) - 1)}
                    startIcon={<ChevronLeftIcon />}>
                    Prev
                </Button>

                <Button
                    className={`${formStyle.button} ${formStyle.outlinedButton}`}
                    disableRipple
                    disabled={!!page.last}
                    onClick={() => onPageChange((page.number ?? 0) + 1)}
                    endIcon={<ChevronRightIcon />}>
                    Next
                </Button>

                {additionalActionName &&
                    <Button
                        className={`${formStyle.button} ${formStyle.raisedButton}`}
                        disableRipple
                        onClick={() => onAdditionalActionClick?.()}>
                        {additionalActionName}
                    </Button>
                }

            </div>
        </div>
    )
}