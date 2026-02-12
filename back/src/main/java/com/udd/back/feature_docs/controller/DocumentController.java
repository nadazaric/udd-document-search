package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import com.udd.back.index.FileIndexRepository;
import com.udd.back.index.model.ForensicReport;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired ParseFileService parseFileService;
    @Autowired FileService fileService;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @Autowired FileIndexRepository fileIndexRepository;

    @PostMapping("/parse")
    public ResponseEntity<IndexDocumentDTO> parseDocument(
            @ModelAttribute ParseDocumentDTO documentDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String username = jwtTokenUtil.getUsername(jwt);

        IndexDocumentDTO indexDocumentDTO = parseFileService.parse(documentDTO.getFile(), username);
        fileService.store(documentDTO.getFile(), indexDocumentDTO.getId().toString());

        return new ResponseEntity<>(indexDocumentDTO, HttpStatus.CREATED) ;
    }

    @PostMapping("/parse/confirm")
    public ResponseEntity<IndexDocumentDTO> confirmParse(@RequestBody IndexDocumentDTO indexDocumentDTO) {

        ForensicReport forensicReport = new ForensicReport();
        forensicReport.setId(indexDocumentDTO.getId());
        forensicReport.setMalwareOrThreatName("Test");

        fileIndexRepository.save(forensicReport);

        return new ResponseEntity<>(indexDocumentDTO, HttpStatus.OK);
    }

}