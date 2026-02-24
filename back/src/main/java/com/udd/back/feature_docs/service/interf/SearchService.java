package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.dto.SearchByAnalystHashClassificationRequestDTO;
import com.udd.back.feature_docs.dto.SearchByOrganizationThreatNameDTO;
import com.udd.back.feature_docs.dto.SearchKnnRequestDTO;
import com.udd.back.feature_docs.dto.SearchSimpleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

    Page<SearchSimpleResponseDTO> searchByAnalystHashClassification(SearchByAnalystHashClassificationRequestDTO req, Pageable pageable);

    Page<SearchSimpleResponseDTO> searchByOrganizationThreatName(SearchByOrganizationThreatNameDTO req, Pageable pageable);

    Page<SearchSimpleResponseDTO> searchKnn(SearchKnnRequestDTO req, Pageable pageable);

}
