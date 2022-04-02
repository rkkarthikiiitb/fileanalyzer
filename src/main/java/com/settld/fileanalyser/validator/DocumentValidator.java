package com.settld.fileanalyser.validator;

import com.settld.fileanalyser.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class DocumentValidator {
    public void verifyDocumentIntegrity(PDDocument pdfDocument, InputStream stream) throws ValidationException, IOException {
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();
        MediaType mediaType = detector.detect(stream, metadata);

        if (!mediaType.equals(MediaType.parse("application/pdf"))) {
            log.error("Please use only PDF files for analysis.");
            throw new ValidationException("Invalid format detected. Aborting document analysis!");
        }
        if (pdfDocument.isEncrypted()) {
            log.error("PDF file is encrypted, cannot perform analysis on encrypted files");
            throw new ValidationException("PDF File is encrypted, aborting analysis");
        }
    }
}