package com.udd.back.feature_docs.dto;

public class IndexDocumentDTO {
    private String name;
    private String type;
    private Boolean isEmpty;

    public IndexDocumentDTO() {}
    public IndexDocumentDTO(String name, String type, Boolean isEmpty) {
        this.name = name;
        this.type = type;
        this.isEmpty = isEmpty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
