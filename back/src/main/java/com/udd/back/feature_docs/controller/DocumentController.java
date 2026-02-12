package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired ParseFileService parseFileService;
    @Autowired FileService fileService;

    @PostMapping("/parse")
    @ResponseStatus(HttpStatus.CREATED)
    public IndexDocumentDTO parseDocument(ParseDocumentDTO documentDTO) {
        IndexDocumentDTO indexDocumentDTO = parseFileService.parse(documentDTO.getFile());
        fileService.store(documentDTO.getFile(), indexDocumentDTO.getId().toString());
        return indexDocumentDTO;
    }

}