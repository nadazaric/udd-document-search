package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_auth.model.User;
import com.udd.back.feature_auth.service.interf.UserService;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.repository.FileMetadataRepository;
import com.udd.back.feature_docs.service.interf.FileMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
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

    @Override
    public void changeStatus(UUID id, FileStatus fileStatus) {
        Optional<FileMetadata> fileMetadata = fileMetadataRepository.findById(id);
        if (fileMetadata.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, id.toString());

        fileMetadata.get().setStatus(fileStatus);
        fileMetadataRepository.save(fileMetadata.get());
    }

}
