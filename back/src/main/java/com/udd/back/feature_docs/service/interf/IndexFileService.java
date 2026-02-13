package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;

import java.util.UUID;

public interface IndexFileService {
    IndexDocumentDTO index(IndexDocumentDTO indexDocumentDTO);

    void reject(UUID id);

}
