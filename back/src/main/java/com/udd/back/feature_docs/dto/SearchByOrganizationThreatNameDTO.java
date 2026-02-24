package com.udd.back.feature_docs.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchByOrganizationThreatNameDTO {

    @NotBlank
    private String certOrganization;

    @NotBlank
    private String malwareOrThreatName;

    public SearchByOrganizationThreatNameDTO() {

    }

    public SearchByOrganizationThreatNameDTO(String certOrganization, String malwareOrThreatName) {
        this.certOrganization = certOrganization;
        this.malwareOrThreatName = malwareOrThreatName;
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
