package com.udd.back.feature_docs.dto;

import org.springframework.web.multipart.MultipartFile;

public class ParseDocumentDTO {
    private MultipartFile file;

    public  ParseDocumentDTO() { }
    public ParseDocumentDTO(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
