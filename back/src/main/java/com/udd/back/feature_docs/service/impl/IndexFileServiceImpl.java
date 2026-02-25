package com.udd.back.feature_docs.service.impl;

import ai.djl.translate.TranslateException;
import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.model.GeocodeResult;
import com.udd.back.feature_docs.service.interf.*;
import com.udd.back.feature_docs.util.VectorizationUtil;
import com.udd.back.index.model.ForensicReport;
import com.udd.back.index.repository.FileIndexRepository;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class IndexFileServiceImpl implements IndexFileService {

    @Autowired FileIndexRepository fileIndexRepository;
    @Autowired FileMetadataService fileMetadataService;
    @Autowired GeocodingService geocodingService;
    @Autowired PdfExtractService pdfExtractService;
    @Autowired FileService fileService;
    @Autowired VectorizationUtil vectorizationUtil;
    private static final org.slf4j.Logger EVENT_LOGGER =
            org.slf4j.LoggerFactory.getLogger("udd.events");

    @Override
    public IndexDocumentDTO index(IndexDocumentDTO indexDocumentDTO) {
        Optional<FileMetadata> fileMetadata = fileMetadataService.getById(indexDocumentDTO.getId());
        FileStatus fileStatus = fileMetadata.get().getStatus();

        if (fileStatus != FileStatus.PARSED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("File status is CONFIRMED or REJECTED. File ID: %s.", indexDocumentDTO.getId()));
        }

        Optional<GeocodeResult> geocodeResult = geocodingService.geocode(indexDocumentDTO.getAddress());
        if (geocodeResult.isEmpty()) return indexDocumentDTO;

        ForensicReport forensicReport = new ForensicReport();

        forensicReport.setId(indexDocumentDTO.getId());
        forensicReport.setForensicAnalystName(indexDocumentDTO.getForensicAnalystName());
        forensicReport.setCertOrganization(indexDocumentDTO.getCertOrganization());
        forensicReport.setAddress(indexDocumentDTO.getAddress());
        forensicReport.setGeoPoint(geocodeResult.get().getGeoPoint());
        forensicReport.setMalwareOrThreatName(indexDocumentDTO.getMalwareOrThreatName());
        forensicReport.setBehaviorDescription(indexDocumentDTO.getBehaviorDescription());
        forensicReport.setThreatClassification(indexDocumentDTO.getThreatClassification().toString());
        forensicReport.setHash(indexDocumentDTO.getHash());

        MultipartFile file = fileService.getFile(indexDocumentDTO.getId().toString());
        String content = pdfExtractService.extractText(file);
        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("File is empty. File ID: %s.", indexDocumentDTO.getId()));
        }
        forensicReport.setContent(content);

        try {
            forensicReport.setVectorizedContent(vectorizationUtil.getEmbedding(content));
        } catch (TranslateException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("Could not calculate vector representation. File ID: %s.", indexDocumentDTO.getId()));
        }

        fileMetadataService.changeStatus(indexDocumentDTO.getId(), FileStatus.CONFIRMED);

        fileIndexRepository.save(forensicReport);
        indexDocumentDTO.setGeocoded(true);

        EVENT_LOGGER.info("{\"event\":\"DOC_INDEXED\",\"timestamp\":\"{}\",\"city\":\"{}\",\"forensicAnalystName\":\"{}\",\"malwareOrThreatName\":\"{}\"}", java.time.Instant.now(), geocodeResult.get().getCity(), forensicReport.getForensicAnalystName(), forensicReport.getMalwareOrThreatName());

        return indexDocumentDTO;
    }

    @Override
    public void reject(UUID id) {
        Optional<FileMetadata> fileMetadata = fileMetadataService.getById(id);
        if (fileMetadata.isEmpty() || fileMetadata.get().getStatus() != FileStatus.PARSED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("File status is PARSED or REJECTED. File ID: %s.", id));
        }

        fileMetadataService.changeStatus(id, FileStatus.REJECTED);
    }

}
