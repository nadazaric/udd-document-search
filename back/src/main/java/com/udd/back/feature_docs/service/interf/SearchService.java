package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    Page<SearchBaseResponseDTO> searchByAnalystHashClassification(SearchByAnalystHashClassificationRequestDTO req, Pageable pageable);

    Page<SearchBaseResponseDTO> searchByOrganizationThreatName(SearchByOrganizationThreatNameDTO req, Pageable pageable);

    Page<SearchKnnResponseDTO> searchKnn(SearchTextRequestDTO req, Pageable pageable);

    Page<SearchContentResponse> searchFullText(SearchTextRequestDTO req, Pageable pageable);

    Page<SearchBooleanResponseDTO> combineBoolean(SearchTextRequestDTO req, Pageable pageable);

    Page<SearchBaseResponseDTO> searchByAddress(SearchByLocationRequestDTO req, Pageable pageable);

}
