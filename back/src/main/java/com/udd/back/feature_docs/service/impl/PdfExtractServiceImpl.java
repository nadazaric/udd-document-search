package com.udd.back.feature_docs.service.impl;

import com.udd.back.feature_docs.service.interf.PdfExtractService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfExtractServiceImpl implements PdfExtractService {

    @Override
    public String extractText(MultipartFile file) {
        byte[] pdfBytes = getDocumentBytes(file);

        try (PDDocument doc = PDDocument.load(pdfBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(doc);
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract text from PDF", e);
        }
    }

    private byte[] getDocumentBytes(MultipartFile file) {
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read uploaded file", e);
        }

        return  bytes;
    }

}
