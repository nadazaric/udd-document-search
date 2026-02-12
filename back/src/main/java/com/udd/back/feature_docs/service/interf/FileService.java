package com.udd.back.feature_docs.service.interf;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void store(MultipartFile file, String serverFilename);

    byte[] getBytes(String fileName);

}
