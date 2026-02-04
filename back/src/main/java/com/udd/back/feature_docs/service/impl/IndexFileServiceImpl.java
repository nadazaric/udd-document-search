package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.IndexFileService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IndexFileServiceImpl implements IndexFileService {

    private final FileService fileService;

    public IndexFileServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void index(ParseDocumentDTO documentDTO) {
        String serverFilename = UUID.randomUUID().toString();
        fileService.store(documentDTO.getFile(), serverFilename);
    }
}
