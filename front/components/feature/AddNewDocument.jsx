import { Button } from "@mui/material"
import UploadFile from "../widgets/UploadFile"
import formStyle from '../../styles/Form.module.css'
import { useState, useMemo } from "react"
import axios from 'axios'
import { BACK_BASE_URL } from '@/values/Enviroment'
import AddNewDocumentForm from "./AddNewDocumentForm"

export function AddNewDocument() {

    const [file, setFile] = useState(null)
    const [parseButtonDisabled, setParseButtonDisabled] = useState(true)
    const [parseData, setParseData] = useState(null)

    // ------------------------------------------------------------------ Upload File
    function onFileSelected(file) {
        setFile(file)
        setParseButtonDisabled(false)
    }

    function onFileSelectedError() {
        setFile(null)
        setParseButtonDisabled(true)
    }

    async function parseDocument() {
        try {
            const formData = new FormData()
            formData.append("file", file)

            const response = await axios.post(`${BACK_BASE_URL}/document/parse`, formData)
            setParseData(response.data)
        } catch (error) {
            console.log(error)
            setParseButtonDisabled(true)
        }
    }

    // ------------------------------------------------------------------ Index File
    const isNonEmpty = (x) => String(x ?? "").trim().length > 0

    const isFormValid = useMemo(() => {
        if (!parseData) return false
        return (
            isNonEmpty(parseData.forensicAnalystName) &&
            isNonEmpty(parseData.certOrganization) &&
            isNonEmpty(parseData.malwareOrThreatName) &&
            isNonEmpty(parseData.hash) &&
            isNonEmpty(parseData.threatClassification) &&
            isNonEmpty(parseData.behaviorDescription)
        )
    }, [parseData])

    if (!parseData) {
        return(
            <div>
                <UploadFile 
                    onFileSelected={file => onFileSelected(file)}
                    onError={error => onFileSelectedError()} />

                <div className="spacer-h-s"/>

                <div className={formStyle.buttonsWrapperToRight}> 
                    <Button 
                        className={`${formStyle.button} ${formStyle.raisedButton}`}
                        disableRipple
                        disabled={parseButtonDisabled}
                        onClick={() => parseDocument()} >
                        Parse Document
                    </Button>
                </div>
            </div>
        )
    } else {
        return(
            <div>
                <AddNewDocumentForm 
                    formData={parseData}
                    onFormChange={(form) => setParseData(form)} />

                <div className="spacer-h-s"/>

                <div className={formStyle.buttonsWrapperToRight}> 
                    <Button 
                        className={`${formStyle.button} ${formStyle.outlinedButton}`}
                        disableRipple
                        onClick={() => parseDocument()} >
                        Reject Indexing
                    </Button>

                    <Button 
                        className={`${formStyle.button} ${formStyle.raisedButton}`}
                        disableRipple
                        disabled={!isFormValid}
                        onClick={() => parseDocument()} >
                        Index Document
                    </Button>
                </div>
            </div>
        )
    }

}