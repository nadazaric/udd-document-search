import React, { createContext, useCallback, useContext, useMemo, useState } from "react"
import { Popup } from "./Popup"

const PopupContext = createContext(null)

export function PopupProvider({ children }) {
    const [state, setState] = useState({
        open: false,
        message: "",
        severity: "info",
        duration: 3000,
        id: 0,
    })

    const hidePopup = useCallback(() => {
        setState((prev) => ({ ...prev, open: false }))
    }, [])

    const showPopup = useCallback(({ message, severity = "info", duration = 3000 }) => {
        setState((prev) => ({
            id: prev.id + 1,
            open: true,
            message: String(message ?? ""),
            severity,
            duration,
        }))
    }, [])

    const ctx = useMemo(() => ({ showPopup, hidePopup }), [showPopup, hidePopup])

    return (
        <PopupContext.Provider value={ctx}>
            {children}

            <Popup
                key={state.id}
                open={state.open}
                message={state.message}
                severity={state.severity}
                duration={state.duration}
                onClose={hidePopup} />
        </PopupContext.Provider>
    )
}

export function usePopup() {
    const ctx = useContext(PopupContext)
    if (!ctx) throw new Error("usePopup must be used inside <PopupProvider>")
    return ctx
}
