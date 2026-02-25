package com.udd.back.feature_docs.dto;

import java.util.List;

public class SearchContentResponse extends SearchBaseResponseDTO{
    private List<String> contentHighlights;

    public SearchContentResponse(SearchBaseResponseDTO responseDTO) {
        super(
            responseDTO.getId(),
            responseDTO.getForensicAnalystName(),
            responseDTO.getHash(),
            responseDTO.getThreatClassification(),
            responseDTO.getCertOrganization(),
            responseDTO.getMalwareOrThreatName(),
            responseDTO.getAddress()
        );
    }

    public List<String> getContentHighlights() {
        return contentHighlights;
    }

    public void setContentHighlights(List<String> contentHighlights) {
        this.contentHighlights = contentHighlights;
    }

}
