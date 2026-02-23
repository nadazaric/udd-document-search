import styles from "../../styles/OptionsPanel.module.css"

export function OptionsPanel({
    options,
    selectedKey,
    onSelect,
    className = ""
}) {
    return (
        <div className={`${styles.wrapper} ${className}`}>
            <div className={styles.navList}>
                {options.map((opt) => {
                    const isActive = opt.key === selectedKey

                    return (
                        <div
                            key={opt.key}
                            className={`${styles.navItem} ${isActive ? styles.navItemActive : ""}`}
                            onClick={() => onSelect(opt.key) }
                            role="button"
                            tabIndex={0} >
                            <div className={styles.navItemIndicator} />
                            <div className={styles.navItemLabel}>{opt.label}</div>
                            {opt.hint ? <div className={styles.navItemHint}>{opt.hint}</div> : null}
                        </div>
                    )
                })}
            </div>
        </div>
    )
}