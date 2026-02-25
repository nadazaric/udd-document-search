package com.udd.back.feature_docs.dto;

public class SearchBooleanResponseDTO extends SearchBaseResponseDTO {

    private String behaviorDescription;

    public SearchBooleanResponseDTO(SearchBaseResponseDTO searchBaseResponseDTO) {
        super(
                searchBaseResponseDTO.getId(),
                searchBaseResponseDTO.getForensicAnalystName(),
                searchBaseResponseDTO.getHash(),
                searchBaseResponseDTO.getThreatClassification(),
                searchBaseResponseDTO.getCertOrganization(),
                searchBaseResponseDTO.getMalwareOrThreatName(),
                searchBaseResponseDTO.getAddress());
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }
}
