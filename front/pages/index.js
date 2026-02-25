import Head from "next/head"
import style from "../styles/Search.module.css"
import { Button } from "@mui/material";
import formStyle from "../styles/Form.module.css"
import { DialogWithHeader } from "@/components/widgets/Dialog";
import { useState } from "react";
import { LABEL } from "@/values/Labels";
import SearchForm from "@/components/feature/SearchForm";
import IndexInfoCard from "@/components/feature/IndexInfoCard";
import axios from "axios";
import { BACK_BASE_URL } from "@/values/Enviroment";
import Paginator from "@/components/widgets/Paginator";
import { usePopup } from "@/components/widgets/PopupProvider";
import { SEARCH_PAGE, SEVERITY } from "@/helpers/Enums";
import { ERROR, INFO } from "@/values/Messages";
import DocumentDetails from "@/components/feature/DocumentDetails";

export default function Home() {

  const [openSearchDialog, setOpenSearchDialog] = useState(false)
  const [deatilsDialogOpen, setDeatilsDialogOpen] = useState(false)
  const [selectedDocument, setSelectedDocument] = useState(null)
  const [searchRequest, setSearchRequest] = useState(null)
  const [results, setResults] = useState(null)
  const [clickable, setClickable] = useState(false)
  const { showPopup } = usePopup()
  const pageSize = 9;

  function generateUrl(mode) {
    if (mode === SEARCH_PAGE.ANALYST_HASH_CLASS) return "by-analyst-hash-classification"
    if (mode === SEARCH_PAGE.ORG_THREAT) return "by-organization-threat-name"
    if (mode === SEARCH_PAGE.KNN_FREE_TEXT) return "knn"
    if (mode === SEARCH_PAGE.FULLTEXT_PDF) return "full-text"
    if (mode === SEARCH_PAGE.BOOLEAN_SEMI_STRUCTURED) return "boolean"
    return "by-location"
  }

  function isClickableMode(mode) {
    return (
      mode === SEARCH_PAGE.KNN_FREE_TEXT ||
      mode === SEARCH_PAGE.FULLTEXT_PDF ||
      mode === SEARCH_PAGE.BOOLEAN_SEMI_STRUCTURED
    )
  }

  async function runSearch(req) {
    const { mode, payload, page, size } = req
    const params = { page, size }

    setClickable(isClickableMode(mode))

    try {
      const response = await axios.post(`${BACK_BASE_URL}/search/${generateUrl(mode)}`, payload, { params })

      if (!response.data.totalElements) {
        showPopup({ message: INFO.DOCUMENTS_NOT_FOUND, severity: SEVERITY.WARNING })
      }

      setSearchRequest(req)
      setResults(response.data)

    } catch (error) {
      const status = error?.response?.status

      showPopup({
        message: status === 400
          ? ERROR.WRONG_PARAMETERS
          : status === 422
            ? ERROR.ADDRESS_NOT_FOUND
            : ERROR.SERVER,
        severity: SEVERITY.ERROR
      })

      setResults({ content: [], totalElements: 0, totalPages: 0, first: true, last:true, number: 0})
    }
  }

  async function onSearchSubmit(mode, payload) {
    await runSearch({ mode, payload, page: 0, size: pageSize })
    setOpenSearchDialog(false)
  }

  async function goToPage(nextPage) {
    if (!searchRequest) return
    await runSearch({ ...searchRequest, page: nextPage })
  }

  function openDetailsDialog(item) {
    console.log(item)
    setDeatilsDialogOpen(true)
    setSelectedDocument(item)
  }

  return (
    <>
      <Head>
        <title>{LABEL.DOCUMENT_SEARCH}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main className={style.page}>

        {!results &&
          <div className={style.descriptionPage}>
            <div className={style.descriptionWrapper}>
              <div className={style.descriptionTitle}>{LABEL.NOTHING_HERE_TITLE}</div>

              <div className="spacer-h-s" />

              <div>{LABEL.NOTHING_HERE_DESCRIPTION}</div>

              <div className="spacer-h-l" />

              <Button
                className={`${formStyle.button} ${formStyle.raisedButton} ${style.descriptionButton}`}
                disableRipple
                onClick={() => setOpenSearchDialog(true)} >
                {LABEL.SEARCH}
              </Button>
            </div>
          </div>
        }

        {results &&
          <div className={style.resultsPage}>
            <div className={style.results}>
              {results.content?.map((doc) => (
                <IndexInfoCard
                  key={doc.id}
                  item={doc}
                  isClickable={clickable}
                  onClick={openDetailsDialog} />
              ))}
            </div>

            <div className="spacer-h-m" />

            <Paginator
              page={results}
              onPageChange={(nextPage) => goToPage(nextPage)}
              additionalActionName={LABEL.NEW_SEARCH}
              onAdditionalActionClick={() => setOpenSearchDialog(true)} />
          </div>
        }

        <DialogWithHeader
          isOpen={openSearchDialog}
          width={900}
          onCloseModal={() => setOpenSearchDialog(false)}
          title={LABEL.SEARCH_DOCUMENTS} >
          <SearchForm
            isOpen={openSearchDialog}
            onSubmit={onSearchSubmit} />
        </DialogWithHeader>

        <DialogWithHeader
          isOpen={deatilsDialogOpen}
          width={700}
          onCloseModal={() => {
            setDeatilsDialogOpen(false)
            setSelectedDocument(null)
          }}
          title={LABEL.DOCUMENT_DETAILS} >
          <DocumentDetails
            document={selectedDocument} />
        </DialogWithHeader>

      </main>
    </>
  );
}
