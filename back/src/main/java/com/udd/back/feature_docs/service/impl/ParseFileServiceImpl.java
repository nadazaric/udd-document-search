package com.udd.back.feature_docs.service.impl;

import com.udd.back.core.constants.RegexPattern;
import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.Classification;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.service.interf.FileMetadataService;
import com.udd.back.feature_docs.service.interf.FileService;
import com.udd.back.feature_docs.service.interf.ParseFileService;
import com.udd.back.feature_docs.service.interf.PdfExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private IndexDocumentDTO parse(MultipartFile file) throws Exception {
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
