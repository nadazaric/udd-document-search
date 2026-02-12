package com.udd.back.index;

import com.udd.back.index.model.ForensicReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileIndexRepository extends ElasticsearchRepository<ForensicReport, UUID> {
}
