package com.udd.back.index.repository;

import com.udd.back.index.model.ForensicReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileIndexRepository extends ElasticsearchRepository<ForensicReport, UUID> {
}
