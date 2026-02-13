package com.udd.back.feature_docs.service.interf;

import org.springframework.web.multipart.MultipartFile;

public interface PdfExtractService {
    String extractText(MultipartFile file);
}
