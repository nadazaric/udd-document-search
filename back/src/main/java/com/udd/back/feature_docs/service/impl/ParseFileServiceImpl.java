package com.udd.back.feature_docs.service.impl;

import com.udd.back.core.constants.RegexPattern;
import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.Classification;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
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

import java.util.Objects;
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

        String certOrganization = regexMatch(RegexPattern.ORG_AND_ADDRESS, 1);
        String address = regexMatch(RegexPattern.ORG_AND_ADDRESS, 2);

        String malwareOrThreatName = regexMatch(RegexPattern.THREAT);

        String clsRaw = regexMatch(RegexPattern.CLASS_AND_HASH, 1);
        String hash = regexMatch(RegexPattern.CLASS_AND_HASH, 2);

        String behaviorDescription = Objects.requireNonNull(regexMatch(RegexPattern.BEHAVIOR_AND_ANALYST, 1)).replaceAll("\\n+", "");
        String forensicAnalystName = regexMatch(RegexPattern.BEHAVIOR_AND_ANALYST, 2);

        Classification classification = mapClassification(clsRaw);

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
        return regexMatch(pattern, 1);
    }

    private String regexMatch(Pattern pattern, int group) {
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(group).trim() : null;
    }

    private Classification mapClassification(String clsRaw) {
        if (clsRaw == null) return null;

        String v = clsRaw.trim().toLowerCase();

        return switch (v) {
            case "niska" -> Classification.LOW;
            case "srednja" -> Classification.MEDIUM;
            case "visoka" -> Classification.HIGH;
            case "kritična" -> Classification.CRITICAL;
            case "ниска" -> Classification.LOW;
            case "средња" -> Classification.MEDIUM;
            case "висока" -> Classification.HIGH;
            case "критична" -> Classification.CRITICAL;
            default -> null;
        };

    }

}
