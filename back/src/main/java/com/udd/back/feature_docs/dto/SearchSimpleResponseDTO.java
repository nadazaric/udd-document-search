package com.udd.back.feature_docs.dto;

import com.udd.back.feature_docs.enumeration.Classification;

import java.util.UUID;

public class SearchSimpleResponseDTO {

    private UUID id;
    private String forensicAnalystName;
    private String hash;
    private Classification threatClassification;
    private String certOrganization;
    private String malwareOrThreatName;

    public  SearchSimpleResponseDTO() {

    }

    public SearchSimpleResponseDTO(UUID id, String forensicAnalystName, String hash, Classification threatClassification, String certOrganization, String malwareOrThreatName) {
        this.id = id;
        this.forensicAnalystName = forensicAnalystName;
        this.hash = hash;
        this.threatClassification = threatClassification;
        this.certOrganization = certOrganization;
        this.malwareOrThreatName = malwareOrThreatName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getCertOrganization() {
        return certOrganization;
    }

    public void setCertOrganization(String certOrganization) {
        this.certOrganization = certOrganization;
    }

    public String getMalwareOrThreatName() {
        return malwareOrThreatName;
    }

    public void setMalwareOrThreatName(String malwareOrThreatName) {
        this.malwareOrThreatName = malwareOrThreatName;
    }
}
