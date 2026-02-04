package com.udd.back.feature_docs.dto;

import com.udd.back.feature_docs.enumeration.Severity;

public class IndexDocumentDTO {
    private String forensicAnalystName;
    private String certOrganization;
    private String malwareOrThreatName;
    private Severity threatClassification;
    private String address;
    private String hash;

    public IndexDocumentDTO() {
    }

    public IndexDocumentDTO(String forensicAnalystName, String certOrganization, String malwareOrThreatName, Severity threatClassification, String address, String hash) {
        this.forensicAnalystName = forensicAnalystName;
        this.certOrganization = certOrganization;
        this.malwareOrThreatName = malwareOrThreatName;
        this.threatClassification = threatClassification;
        this.address = address;
        this.hash = hash;
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

    public Severity getThreatClassification() {
        return threatClassification;
    }

    public void setThreatClassification(Severity threatClassification) {
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