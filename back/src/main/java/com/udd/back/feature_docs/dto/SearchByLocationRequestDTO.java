package com.udd.back.feature_docs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchByLocationRequestDTO {

    @NotBlank
    private String address;

    @NotNull
    @Min(1)
    private Integer distance;

    public SearchByLocationRequestDTO() {

    }

    public SearchByLocationRequestDTO(String address, Integer distance) {
        this.address = address;
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
