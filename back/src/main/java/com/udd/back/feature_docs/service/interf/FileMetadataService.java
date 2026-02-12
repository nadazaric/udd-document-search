package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.model.FileMetadata;

public interface FileMetadataService {

    FileMetadata save(String authorUsername) throws Exception;

}
