package com.udd.back.feature_docs.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchKnnRequestDTO {
    @NotBlank
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
