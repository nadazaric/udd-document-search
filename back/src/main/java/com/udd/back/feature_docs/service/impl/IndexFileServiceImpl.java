package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.model.FileMetadata;
import com.udd.back.feature_docs.service.interf.*;
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

    @Override
    public IndexDocumentDTO index(IndexDocumentDTO indexDocumentDTO) {
        Optional<FileMetadata> fileMetadata = fileMetadataService.getById(indexDocumentDTO.getId());
        FileStatus fileStatus = fileMetadata.get().getStatus();

        if (fileStatus != FileStatus.PARSED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("File status is CONFIRMED or REJECTED. File ID: %s.", indexDocumentDTO.getId()));
        }

        Optional<GeoPoint> geoPoint = geocodingService.geocode(indexDocumentDTO.getAddress());
        if (geoPoint.isEmpty()) return indexDocumentDTO;

        ForensicReport forensicReport = new ForensicReport();

        forensicReport.setId(indexDocumentDTO.getId());
        forensicReport.setForensicAnalystName(indexDocumentDTO.getForensicAnalystName());
        forensicReport.setCertOrganization(indexDocumentDTO.getCertOrganization());
        forensicReport.setAddress(indexDocumentDTO.getAddress());
        forensicReport.setGeoPoint(geoPoint.get());
        forensicReport.setMalwareOrThreatName(indexDocumentDTO.getMalwareOrThreatName());
        forensicReport.setBehaviorDescription(indexDocumentDTO.getBehaviorDescription());
        forensicReport.setThreatClassification(indexDocumentDTO.getThreatClassification().toString());
        forensicReport.setHash(indexDocumentDTO.getHash());

        MultipartFile file = fileService.getFile(indexDocumentDTO.getId().toString());
        String content = pdfExtractService.extractText(file);
        if (content == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("File is empty. File ID: %s.", indexDocumentDTO.getId()));
        }
        forensicReport.setContent(content);

        fileMetadataService.changeStatus(indexDocumentDTO.getId(), FileStatus.CONFIRMED);

        fileIndexRepository.save(forensicReport);
        indexDocumentDTO.setGeocoded(true);

        return  indexDocumentDTO;
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
