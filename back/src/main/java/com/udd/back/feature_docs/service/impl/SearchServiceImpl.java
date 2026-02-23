package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.dto.SearchByAnalystHashClassificationRequestDTO;
import com.udd.back.feature_docs.dto.SearchByOrganizationThreatNameDTO;
import com.udd.back.feature_docs.dto.SearchSimpleResponseDTO;
import com.udd.back.feature_docs.enumeration.Classification;
import com.udd.back.feature_docs.service.interf.SearchService;
import com.udd.back.index.model.ForensicReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ElasticsearchOperations elasticsearchTemplate;

    public SearchServiceImpl(ElasticsearchOperations elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<SearchSimpleResponseDTO> searchByAnalystHashClassification(
            SearchByAnalystHashClassificationRequestDTO req,
            Pageable pageable
    ) {
        String analyst = req.getForensicAnalystName().trim();
        String hash = req.getHash().trim();
        String classification = req.getThreatClassification().name();

        BoolQuery.Builder bool = new BoolQuery.Builder();

        if (isPhrase(analyst)) {
            bool.must(m -> m.matchPhrase(mp -> mp.field("forensicAnalystName").query(stripQuotes(analyst))));
        } else {
            bool.must(m -> m.match(mm -> mm.field("forensicAnalystName").query(analyst)));
        }
        bool.must(m -> m.term(t -> t.field("hash").value(hash)));
        bool.must(m -> m.term(t -> t.field("threatClassification").value(classification)));

        Query query = bool.build()._toQuery();

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .withHighlightQuery(buildHighlightQuery(List.of("forensicAnalystName")))
                .build();

        return runQuery(nativeQuery, ForensicReport.class, pageable, this::mapToResponseDTO);
    }

    @Override
    public Page<SearchSimpleResponseDTO> searchByOrganizationThreatName(
            SearchByOrganizationThreatNameDTO req,
            Pageable pageable
    ) {
        String organization = req.getCertOrganization().trim();
        String threatName = req.getMalwareOrThreatName().trim();

        BoolQuery.Builder bool = new BoolQuery.Builder();

        if (isPhrase(organization)) {
            bool.must(m -> m.matchPhrase(mp -> mp.field("certOrganization").query(stripQuotes(organization))));
        } else {
            bool.must(m -> m.match(mm -> mm.field("certOrganization").query(organization)));
        }

        if (isPhrase(threatName)) {
            bool.must(m -> m.matchPhrase(mp -> mp.field("malwareOrThreatName").query(stripQuotes(threatName))));
        } else {
            bool.must(m -> m.match(mm -> mm.field("malwareOrThreatName").query(threatName)));
        }

        Query query = bool.build()._toQuery();

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .withHighlightQuery(buildHighlightQuery(List.of("certOrganization", "malwareOrThreatName")))
                .build();

        return runQuery(nativeQuery, ForensicReport.class, pageable, this::mapToResponseDTO);
    }

    private HighlightQuery buildHighlightQuery(List<String> fields) {
        HighlightParameters params = HighlightParameters.builder()
                .withPreTags("<em>")
                .withPostTags("</em>")
                .build();

        List<HighlightField> highlightFields = new ArrayList<>();
        fields.forEach(field -> {
            highlightFields.add(new HighlightField(field));
        });

        Highlight highlight = new Highlight(params, highlightFields);

        return new HighlightQuery(highlight, ForensicReport.class);
    }

    private <T, R> Page<R> runQuery(
            NativeQuery query,
            Class<T> clazz,
            Pageable pageable,
            Function<SearchHit<T>, R> mapper
    ) {
        SearchHits<T> hits = elasticsearchTemplate.search(query, clazz);

        List<R> content = hits.getSearchHits().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, hits.getTotalHits());
    }

    private SearchSimpleResponseDTO mapToResponseDTO(SearchHit<ForensicReport> hit) {
        ForensicReport r = hit.getContent();

        String analyst = firstHighlightOrValue(hit, "forensicAnalystName", r.getForensicAnalystName());
        String certOrg = firstHighlightOrValue(hit, "certOrganization", r.getCertOrganization());
        String threat = firstHighlightOrValue(hit, "malwareOrThreatName", r.getMalwareOrThreatName());

        return new SearchSimpleResponseDTO(
                r.getId(),
                analyst,
                r.getHash(),
                Classification.valueOf(r.getThreatClassification()),
                certOrg,
                threat
        );
    }

    private String firstHighlightOrValue(SearchHit<ForensicReport> hit, String field, String fallback) {
        List<String> fragments = hit.getHighlightFields().get(field);
        if (fragments != null && !fragments.isEmpty()) {
            return fragments.get(0);
        }
        return fallback;
    }

    private boolean isPhrase(String value) {
        String v = value.trim();
        return v.length() >= 2 && v.startsWith("\"") && v.endsWith("\"");
    }

    private String stripQuotes(String value) {
        String v = value.trim();
        if (isPhrase(v)) return v.substring(1, v.length() - 1).trim();
        return v;
    }

}
