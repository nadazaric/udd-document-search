package com.udd.back.feature_docs.controller;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.dto.ParseDocumentDTO;
import com.udd.back.feature_docs.service.interf.IndexFileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import com.udd.back.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired ParseFileService parseFileService;
    @Autowired JwtTokenUtil jwtTokenUtil;
    @Autowired IndexFileService indexFileService;

    @PostMapping("/parse")
    public ResponseEntity<IndexDocumentDTO> parseDocument (
            @ModelAttribute ParseDocumentDTO documentDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        String username = jwtTokenUtil.getUsernameFromJWT(jwt);
        IndexDocumentDTO indexDocumentDTO = parseFileService.parseAndStore(documentDTO.getFile(), username);

        return new ResponseEntity<>(indexDocumentDTO, HttpStatus.CREATED) ;
    }

    @PostMapping("/reparse")
    public ResponseEntity<IndexDocumentDTO> reparseDocument (@RequestParam UUID id) throws Exception {
        return new ResponseEntity<>(parseFileService.reparse(id), HttpStatus.CREATED);
    }

    @PostMapping("/parse/confirm")
    public ResponseEntity<IndexDocumentDTO> confirmParse(@RequestBody IndexDocumentDTO indexDocumentDTO) {
        return new ResponseEntity<>(indexFileService.index(indexDocumentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/parse/reject")
    public ResponseEntity<Void> rejectParse(@RequestParam UUID id) {
        indexFileService.reject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}