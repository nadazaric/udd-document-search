package com.udd.back.feature_docs.dto;

import com.udd.back.feature_docs.enumeration.Classification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchByAnalystHashClassificationRequestDTO {

    @NotBlank
    private String forensicAnalystName;

    @NotBlank
    private String hash;

    @NotNull
    private Classification threatClassification;

    public SearchByAnalystHashClassificationRequestDTO() {
    }

    public SearchByAnalystHashClassificationRequestDTO(String forensicAnalystName, String hash, Classification threatClassification) {
        this.forensicAnalystName = forensicAnalystName;
        this.hash = hash;
        this.threatClassification = threatClassification;
    }

    public String getForensicAnalystName() {
        return forensicAnalystName;
    }

    public void setForensicAnalystName(String forensicAnalystName) {
        this.forensicAnalystName = forensicAnalystName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Classification getThreatClassification() {
        return threatClassification;
    }

    public void setThreatClassification(Classification threatClassification) {
        this.threatClassification = threatClassification;
    }

}
