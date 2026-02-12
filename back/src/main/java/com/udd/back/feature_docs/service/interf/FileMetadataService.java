package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;

import java.util.UUID;

public interface FileMetadataService {

    FileMetadata save(String authorUsername) throws Exception;

    void changeStatus(UUID id, FileStatus fileStatus);

}
