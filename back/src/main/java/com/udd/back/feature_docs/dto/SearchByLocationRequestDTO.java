package com.udd.back.feature_docs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchByLocationRequestDTO {

    @NotBlank
    private String address;

    @NotNull
    @Min(1)
    private Integer radius;

    public SearchByLocationRequestDTO() {

    }

    public SearchByLocationRequestDTO(String address, Integer radius) {
        this.address = address;
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
