package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.dto.IndexDocumentDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ParseFileService {

    IndexDocumentDTO parse(MultipartFile file, String authorUsername) throws Exception;

}
