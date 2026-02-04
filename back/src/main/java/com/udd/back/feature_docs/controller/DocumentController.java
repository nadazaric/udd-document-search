package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired FileService fileService;

    @PostMapping("/parse")
    @ResponseStatus(HttpStatus.CREATED)
    public IndexDocumentDTO parseDocument(ParseDocumentDTO documentDTO) {
        fileService.store(documentDTO.getFile(), "test");
        return new IndexDocumentDTO();
    }
}