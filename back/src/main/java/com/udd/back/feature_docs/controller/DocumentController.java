package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @PostMapping("/parse")
    @ResponseStatus(HttpStatus.CREATED)
    public IndexDocumentDTO parseDocument(ParseDocumentDTO documentDTO) {
        return new IndexDocumentDTO(
                documentDTO.getFile().getOriginalFilename(),
                documentDTO.getFile().getContentType(),
                documentDTO.getFile().isEmpty()
        );
    }
}