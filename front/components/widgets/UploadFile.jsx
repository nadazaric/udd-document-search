import { useMemo, useRef, useState, useEffect } from "react"
import CloudUploadOutlinedIcon from "@mui/icons-material/CloudUploadOutlined"
import style from "../../styles/UploadFile.module.css"

export default function UploadFile({ 
    accept = "application/pdf", 
    maxSizeMb = 20, 
    onFileSelected,
    onError, 
    title = "Browse file to upload",
    reset
}) {
  const inputRef = useRef(null)
  const [isDragging, setIsDragging] = useState(false)
  const [error, setError] = useState("")
  const [file, setFile] = useState(null)

  const maxBytes = useMemo(() => maxSizeMb * 1024 * 1024, [maxSizeMb])

  useEffect(() => {
    setIsDragging(false)
    setError("")
    setFile(null)
    if (inputRef.current) inputRef.current.value = ""
  }, [reset])

  function validateAndSet(selected) {
    if (!selected) return

    setError("")

    if (accept && selected.type && !accept.split(",").map(t => t.trim()).includes(selected.type)) {
      setError(`Unsupported file type. Please upload a PDF.`)
      onError?.(error)
      return
    }

    if (selected.size > maxBytes) {
      setError(`File is too large. Max size is ${maxSizeMb} MB.`)
      onError?.(error)
      return
    }

    setFile(selected)
    onFileSelected?.(selected)
  }

  function onInputChange(e) {
    const selected = e.target.files?.[0]
    validateAndSet(selected)
    e.target.value = ""
  }

  function onDrop(e) {
    e.preventDefault()
    e.stopPropagation()
    setIsDragging(false)

    const selected = e.dataTransfer.files?.[0]
    validateAndSet(selected)
  }

  return (
    <div className={style.wrapper}>
      <input ref={inputRef} type="file" accept={accept} onChange={onInputChange} className={style.hiddenInput} />

      <div
        className={`${style.dropzone} ${isDragging ? style.dragging : ""} ${error ? style.error : ""}`}
        role="button"
        tabIndex={0}
        onClick={() => inputRef.current?.click()}
        onKeyDown={(e) => { if (e.key === "Enter" || e.key === " ") inputRef.current?.click() }}
        onDragEnter={(e) => { e.preventDefault(); e.stopPropagation(); setIsDragging(true) }}
        onDragOver={(e) => { e.preventDefault(); e.stopPropagation(); setIsDragging(true) }}
        onDragLeave={(e) => { e.preventDefault(); e.stopPropagation(); setIsDragging(false) }}
        onDrop={onDrop} > 

        <CloudUploadOutlinedIcon 
            className={style.icon}
            fontSize="large" />

        <div className={style.title}>{title}</div>
        <div className={style.subtitle}>Click or drag & drop a PDF here</div>

        {file && !error && (
          <div className={style.fileRow}>
            <span className={style.fileName}>{file.name}</span>
            <span className={style.fileMeta}>{Math.ceil(file.size / 1024)} KB</span>
          </div>
        )}

        {error && <div className={style.errorText}>{error}</div>}
      </div>
    </div>
  )
}
