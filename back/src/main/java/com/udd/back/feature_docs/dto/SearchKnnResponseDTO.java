package com.udd.back.feature_docs.dto;

import com.udd.back.feature_docs.enumeration.Classification;

import java.util.UUID;

public class SearchKnnResponseDTO extends SearchBaseResponseDTO {
    private Float score;
    private String behaviorDescription;

    public SearchKnnResponseDTO(SearchBaseResponseDTO searchBaseResponseDTO) {
        super(
            searchBaseResponseDTO.getId(),
            searchBaseResponseDTO.getForensicAnalystName(),
            searchBaseResponseDTO.getHash(),
            searchBaseResponseDTO.getThreatClassification(),
            searchBaseResponseDTO.getCertOrganization(),
            searchBaseResponseDTO.getMalwareOrThreatName(),
            searchBaseResponseDTO.getAddress())
        ;
    }

    public SearchKnnResponseDTO(UUID id, String forensicAnalystName, String hash, Classification threatClassification, String certOrganization, String malwareOrThreatName, String address, String behaviorDescription) {
        super(id, forensicAnalystName, hash, threatClassification, certOrganization, malwareOrThreatName, address);
        this.behaviorDescription = behaviorDescription;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

}
