package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import com.udd.back.feature_docs.enumeration.FileStatus;
import com.udd.back.feature_docs.service.interf.FileMetadataService;
import com.udd.back.feature_docs.service.interf.GeocodingService;
import com.udd.back.feature_docs.service.interf.IndexFileService;
import com.udd.back.index.model.ForensicReport;
import com.udd.back.index.repository.FileIndexRepository;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IndexFileServiceImpl implements IndexFileService {

    @Autowired FileIndexRepository fileIndexRepository;
    @Autowired FileMetadataService fileMetadataService;
    @Autowired GeocodingService geocodingService;

    @Override
    public IndexDocumentDTO index(IndexDocumentDTO indexDocumentDTO) {

        Optional<GeoPoint> geoPoint = geocodingService.geocode(indexDocumentDTO.getAddress());

        if (geoPoint.isEmpty()) {
            return indexDocumentDTO;
        }

        ForensicReport forensicReport = new ForensicReport();

        forensicReport.setId(indexDocumentDTO.getId());
        forensicReport.setForensicAnalystName(indexDocumentDTO.getForensicAnalystName());
        forensicReport.setCertOrganization(indexDocumentDTO.getCertOrganization());
        forensicReport.setAddress(indexDocumentDTO.getAddress());
        forensicReport.setGeoPoint(geoPoint.get());
        forensicReport.setMalwareOrThreatName(indexDocumentDTO.getMalwareOrThreatName());
        forensicReport.setBehaviorDescription(indexDocumentDTO.getBehaviorDescription());
        forensicReport.setThreatClassification(indexDocumentDTO.getThreatClassification().toString());

        fileMetadataService.changeStatus(indexDocumentDTO.getId(), FileStatus.CONFIRMED);

        fileIndexRepository.save(forensicReport);
        indexDocumentDTO.setGeocoded(true);

        return  indexDocumentDTO;
    }
}
