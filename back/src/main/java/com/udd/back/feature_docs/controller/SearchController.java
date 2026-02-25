package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.*;
import com.udd.back.feature_docs.service.interf.SearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping("/by-analyst-hash-classification")
    public ResponseEntity<Page<SearchBaseResponseDTO>> searchByAnalystHashClassification(
            @RequestBody @Valid SearchByAnalystHashClassificationRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchByAnalystHashClassification(request, pageable), HttpStatus.OK);
    }

    @PostMapping("/by-organization-threat-name")
    public ResponseEntity<Page<SearchBaseResponseDTO>> searchByOrganizationThreatName(
            @RequestBody @Valid SearchByOrganizationThreatNameDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchByOrganizationThreatName(request, pageable), HttpStatus.OK);
    }

    @PostMapping("/knn")
    public ResponseEntity<Page<SearchKnnResponseDTO>> knnSearch(
            @Valid @RequestBody SearchTextRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchKnn(request, pageable), HttpStatus.OK);
    }

    @PostMapping("/full-text")
    public ResponseEntity<Page<SearchContentResponse>> fullTextSearch(
            @Valid @RequestBody SearchTextRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchFullText(request, pageable), HttpStatus.OK);
    }

    @PostMapping("/boolean")
    public ResponseEntity<Page<SearchBooleanResponseDTO>> booleanSearch(
            @Valid @RequestBody SearchTextRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.combineBoolean(request, pageable), HttpStatus.OK);
    }

    @PostMapping("/by-location")
    public ResponseEntity<Page<SearchBaseResponseDTO>> searchByLocation(
            @Valid @RequestBody SearchByLocationRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchByAddress(request, pageable), HttpStatus.OK);
    }

}
