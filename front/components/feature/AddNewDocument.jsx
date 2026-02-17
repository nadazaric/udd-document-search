import { Button } from "@mui/material"
import UploadFile from "../widgets/UploadFile"
import formStyle from "../../styles/Form.module.css"
import animStyle from "../../styles/AddNewDocument.module.css"
import { useMemo, useState, useRef, useEffect } from "react"
import axios from "axios"
import { BACK_BASE_URL } from "@/values/Enviroment"
import AddNewDocumentForm from "./AddNewDocumentForm"

export function AddNewDocument({
    isOpen
}) {
  const [file, setFile] = useState(null)
  const [uploadReset, setUploadReset] = useState(false)
  const [parseButtonDisabled, setParseButtonDisabled] = useState(true)
  const [isFormOpen, setIsFormOpen] = useState(false) 
  const [form, setForm] = useState(null)     

  const wasOpen = useRef(false)

    useEffect(() => {
        let t

        if (isOpen) {
            wasOpen.current = true
            return
        }

        if (wasOpen.current && !isOpen) {
            wasOpen.current = false

            t = setTimeout(() => {
                setFile(null)
                setIsFormOpen(false)
                setForm(null)
                setUploadReset((prev) => !prev)
            }, 500)
        }

        return () => {
            if (t) clearTimeout(t)
        }
    }, [isOpen])

  function onFileSelected(f) {
    setFile(f)
    setParseButtonDisabled(false)
  }

  function onFileSelectedError() {
    setFile(null)
    setParseButtonDisabled(true)
  }

  async function parseDocument() {
    try {
      const fd = new FormData()
      fd.append("file", file)

      const response = await axios.post(`${BACK_BASE_URL}/document/parse`, fd)
      setForm(response.data)   
      setIsFormOpen(true)         
    } catch (error) {
      console.log(error)
      setParseButtonDisabled(true)
    }
  }

  const isNonEmpty = (x) => String(x ?? "").trim().length > 0

  const isFormValid = useMemo(() => {
    if (!form) return false
    return (
      isNonEmpty(form.forensicAnalystName) &&
      isNonEmpty(form.certOrganization) &&
      isNonEmpty(form.malwareOrThreatName) &&
      isNonEmpty(form.hash) &&
      isNonEmpty(form.threatClassification) &&
      isNonEmpty(form.behaviorDescription)
    )
  }, [form])

  return (
    <div className={animStyle.container}>
        <div className={animStyle.viewport}>
            <div className={`${animStyle.track} ${isFormOpen ? animStyle.toForm : animStyle.toUpload}`}>

                <div className={animStyle.step}>
                    <UploadFile 
                        onFileSelected={onFileSelected} 
                        onError={onFileSelectedError}
                        reset={uploadReset} />

                        <div className="spacer-h-s" />
                        
                        <div className={formStyle.buttonsWrapperToRight}>
                        <Button
                            className={`${formStyle.button} ${formStyle.raisedButton}`}
                            disableRipple
                            disabled={parseButtonDisabled}
                            onClick={parseDocument} >
                            Parse Document
                        </Button>
                    </div>
                </div>

                <div className={animStyle.step}>
                    <AddNewDocumentForm
                        formData={form ?? {}}
                        onFormChange={(updatedForm) => setForm(updatedForm)} />

                    <div className="spacer-h-s" />

                    <div className={formStyle.buttonsWrapperToRight}>
                        <Button 
                            className={`${formStyle.button} ${formStyle.outlinedButton}`} 
                            disableRipple >
                            Reject Indexing
                        </Button>

                        <Button
                            className={`${formStyle.button} ${formStyle.raisedButton}`}
                            disableRipple
                            disabled={!isFormValid} >
                            Index Document
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}
