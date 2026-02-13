package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ParseFileService {

    IndexDocumentDTO parseAndStore(MultipartFile file, String authorUsername) throws Exception;

    IndexDocumentDTO reparse(UUID id) throws Exception;

}
