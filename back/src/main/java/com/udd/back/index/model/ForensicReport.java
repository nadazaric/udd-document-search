package com.udd.back.index.model;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.Id;
import java.util.UUID;

@Document(indexName = "threat_intelligence_reports_index")
@Setting(settingPath = "/configuration/serbian-analyzer-config.json")
public class ForensicReport {

    @Id
    private UUID id;


    @Field(type = FieldType.Text, store = true, name = "forensicAnalystName")
    private String forensicAnalystName;


    @Field(type = FieldType.Text, store = true, name = "certOrganization")
    private String certOrganization;


    @GeoPointField
    @Field(store = true, name = "geoPoint")
    private GeoPoint geoPoint;


    @Field(type = FieldType.Text, store = true, name = "malwareOrThreatName")
    private String malwareOrThreatName;


    @Field(type = FieldType.Text, store = true, name = "behaviorDescription")
    private String behaviorDescription;


    @Field(type = FieldType.Keyword, store = true, name = "threatClassification")
    private String threatClassification;


    @Field(type = FieldType.Keyword, store = true, name = "hash")
    private String hash;


    @Field(type = FieldType.Text, store = true, name = "content", analyzer = "serbian_custom", searchAnalyzer = "serbian_custom")
    private String content;


    @Field(type = FieldType.Dense_Vector, dims = 384, name = "vectorizedContent")
    private float[] vectorizedContent;

    public ForensicReport() {}

    public ForensicReport(UUID id, String forensicAnalystName, String certOrganization, GeoPoint geoPoint, String malwareOrThreatName, String behaviorDescription, String threatClassification, String hash, String content, float[] vectorizedContent) {
        this.id = id;
        this.forensicAnalystName = forensicAnalystName;
        this.certOrganization = certOrganization;
        this.geoPoint = geoPoint;
        this.malwareOrThreatName = malwareOrThreatName;
        this.behaviorDescription = behaviorDescription;
        this.threatClassification = threatClassification;
        this.hash = hash;
        this.content = content;
        this.vectorizedContent = vectorizedContent;
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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
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

    public String getThreatClassification() {
        return threatClassification;
    }

    public void setThreatClassification(String threatClassification) {
        this.threatClassification = threatClassification;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float[] getVectorizedContent() {
        return vectorizedContent;
    }

    public void setVectorizedContent(float[] vectorizedContent) {
        this.vectorizedContent = vectorizedContent;
    }

}
