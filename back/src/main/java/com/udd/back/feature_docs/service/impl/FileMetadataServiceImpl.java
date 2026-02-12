package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.repository.FileMetadataRepository;
import com.udd.back.feature_docs.service.interf.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {

    @Autowired FileMetadataRepository fileMetadataRepository;

    @Override
    public FileMetadata save() {
        FileMetadata metadata = fileMetadataRepository.save(new FileMetadata(
                UUID.randomUUID(),
                FileStatus.PARSED,
                null
        ));

        return metadata;
    }

}
