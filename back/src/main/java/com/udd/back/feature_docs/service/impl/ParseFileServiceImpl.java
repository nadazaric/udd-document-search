package com.udd.back.feature_docs.service.impl;

import com.udd.back.core.constants.RegexPattern;
import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.Classification;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.model.InMemoryMultipartFile;
import com.udd.back.feature_docs.service.interf.FileMetadataService;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import com.udd.back.feature_docs.service.interf.PdfExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParseFileServiceImpl implements ParseFileService {

    @Autowired FileService fileService;
    @Autowired PdfExtractService pdfExtractService;
    @Autowired FileMetadataService fileMetadataService;

    private String content;

    @Transactional(rollbackFor = Exception.class)
    public IndexDocumentDTO parseAndStore(MultipartFile file, String authorUsername) throws Exception {
        IndexDocumentDTO indexDocumentDTO = parse(file);

        FileMetadata fileMetadata = fileMetadataService.save(authorUsername);
        indexDocumentDTO.setId(fileMetadata.getId());

        fileService.store(file, fileMetadata.getId().toString());

        return indexDocumentDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    public IndexDocumentDTO reparse(UUID id) throws Exception {
        Optional<FileMetadata> fileMetadata = fileMetadataService.getById(id);
        if (fileMetadata.isEmpty() || fileMetadata.get().getStatus() != FileStatus.REJECTED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("PDF file type is not REJECTED. File ID: %s", id));
        }

        MultipartFile file = fileService.getFile(id.toString());
        IndexDocumentDTO indexDocumentDTO = parse(file);

        indexDocumentDTO.setId(id);

        fileMetadataService.changeStatus(id, FileStatus.PARSED);

        return indexDocumentDTO;
    }

    private IndexDocumentDTO parse(MultipartFile file) {
        content = pdfExtractService.extractText(file);

        String forensicAnalystName = regexMatch(RegexPattern.ANALYST);
        String certOrganization = regexMatch(RegexPattern.ORG);
        String malwareOrThreatName = regexMatch(RegexPattern.MALWARE);
        String behaviorDescription = regexMatch(RegexPattern.DESCRIPTION);
        String threatClassification = regexMatch(RegexPattern.CLASSIFICATION);
        String address = regexMatch(RegexPattern.ADDRESS);
        String hash = regexMatch(RegexPattern.HASH);

        Classification classification = null;
        if (threatClassification != null && !threatClassification.isBlank()) {
            try {
                classification = Classification.valueOf(threatClassification.trim().toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        return new IndexDocumentDTO(
                null,
                forensicAnalystName,
                certOrganization,
                malwareOrThreatName,
                behaviorDescription,
                classification,
                address,
                hash,
                false
        );
    }

    private String regexMatch(Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

}
