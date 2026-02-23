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
import { INFO } from "@/values/Messages";

export default function Home() {

  const [openSearchDialog, setOpenSearchDialog] = useState(false)
  const [searchRequest, setSearchRequest] = useState(null)
  const [results, setResults] = useState(null)
  const { showPopup } = usePopup()
  const pageSize = 9;

  async function runSearch(req) {
    const { mode, payload, page, size } = req

    const params = { page, size }
    let response

    if (mode === SEARCH_PAGE.ANALYST_HASH_CLASS) {
      response = await axios.post(`${BACK_BASE_URL}/search/by-analyst-hash-classification`, payload, { params })
    } else if (mode === SEARCH_PAGE.ORG_THREAT) {
      response = await axios.post(`${BACK_BASE_URL}/search/by-organization-threat-name`, payload, { params })
    } else {
      return
    }

    if (!response.data.totalElements) {
      showPopup({
        message: INFO.DOCUMENTS_NOT_FOUND,
        severity: SEVERITY.WARNING
      })
    }

    setSearchRequest(req)
    setResults(response.data)
  }

  async function onSearchSubmit(mode, payload) {
    await runSearch({ mode, payload, page: 0, size: pageSize })
    setOpenSearchDialog(false)
  }

  async function goToPage(nextPage) {
    if (!searchRequest) return
    await runSearch({ ...searchRequest, page: nextPage })
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
                <IndexInfoCard key={doc.id} item={doc} />
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

      </main>
    </>
  );
}
