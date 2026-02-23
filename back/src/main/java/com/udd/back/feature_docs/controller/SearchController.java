package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.SearchByAnalystHashClassificationRequestDTO;
import com.udd.back.feature_docs.dto.SearchByOrganizationThreatNameDTO;
import com.udd.back.feature_docs.dto.SearchSimpleResponseDTO;
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
    public ResponseEntity<Page<SearchSimpleResponseDTO>> searchByAnalystHashClassification(
            @RequestBody @Valid SearchByAnalystHashClassificationRequestDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchByAnalystHashClassification(request, pageable), HttpStatus.OK);
    }

    @GetMapping("/by-organization-threat-name")
    public ResponseEntity<Page<SearchSimpleResponseDTO>> searchByOrganizationThreatName(
            @RequestBody @Valid SearchByOrganizationThreatNameDTO request,
            Pageable pageable
    ) {
        return new ResponseEntity<>(searchService.searchByOrganizationThreatName(request, pageable), HttpStatus.OK);
    }

}
