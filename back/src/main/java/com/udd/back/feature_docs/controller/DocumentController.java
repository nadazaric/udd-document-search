package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired ParseFileService parseFileService;
    @Autowired FileService fileService;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @PostMapping("/parse")
    @ResponseStatus(HttpStatus.CREATED)
    public IndexDocumentDTO parseDocument(
            @ModelAttribute ParseDocumentDTO documentDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String username = jwtTokenUtil.getUsername(jwt);

        IndexDocumentDTO indexDocumentDTO = parseFileService.parse(documentDTO.getFile(), username);
        fileService.store(documentDTO.getFile(), indexDocumentDTO.getId().toString());

        return indexDocumentDTO;
    }

}