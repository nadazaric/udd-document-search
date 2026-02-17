import style from "@/styles/Chips.module.css"

export default function Chips({
  items = [],
  value = null,
  onChange,
  allowDeselect = false,
  disabled = false,
}) {
  return (
    <div className={style.chipsWrapper}>
      {(items ?? []).map((item) => {
        const isSelected = item.value === value
        return (
          <div
            key={String(item.value)}
            type="button"
            className={`${style.chip} ${isSelected ? style.selected : ""} ${disabled ? style.disabled : ""}`}
            disabled={disabled}
            onClick={() => {
              if (disabled) return
              if (isSelected && allowDeselect) onChange?.(null)
              else onChange?.(item.value)
            }}>
            {item.name}
          </div>
        )
      })}
    </div>
  )
}
