package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.service.interf.UserService;
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
    @Autowired UserService userService;

    @Override
    public FileMetadata save(String authorUsername) throws Exception {
        User user = userService.getByUsername(authorUsername);

        return fileMetadataRepository.save(new FileMetadata(
                UUID.randomUUID(),
                FileStatus.PARSED,
                user
        ));
    }

}
