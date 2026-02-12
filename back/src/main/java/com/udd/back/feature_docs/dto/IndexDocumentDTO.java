package com.udd.back.feature_docs.dto;

import com.udd.back.feature_docs.enumeration.Classification;

import java.util.UUID;

public class IndexDocumentDTO {
    private UUID id;
    private String forensicAnalystName;
    private String certOrganization;
    private String malwareOrThreatName;
    private String behaviorDescription;
    private Classification threatClassification;
    private String address;
    private String hash;

    public IndexDocumentDTO() {
    }

    public IndexDocumentDTO(UUID id, String forensicAnalystName, String certOrganization, String malwareOrThreatName, String behaviorDescription, Classification threatClassification, String address, String hash) {
        this.id = id;
        this.forensicAnalystName = forensicAnalystName;
        this.certOrganization = certOrganization;
        this.malwareOrThreatName = malwareOrThreatName;
        this.behaviorDescription = behaviorDescription;
        this.threatClassification = threatClassification;
        this.address = address;
        this.hash = hash;
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

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public Classification getThreatClassification() {
        return threatClassification;
    }

    public void setThreatClassification(Classification threatClassification) {
        this.threatClassification = threatClassification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
