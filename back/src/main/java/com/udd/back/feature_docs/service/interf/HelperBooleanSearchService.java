package com.udd.back.feature_docs.service.interf;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.Set;

public interface HelperBooleanSearchService {

    Query buildQuery(String expression, Set<String> highlightFields);

}
