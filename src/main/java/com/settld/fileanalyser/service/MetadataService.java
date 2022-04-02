package com.settld.fileanalyser.service;

import com.settld.fileanalyser.dto.DocumentMetaData;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MetadataService {

    public DocumentMetaData extractMetaDataInformation(PDDocument pdfDocument) {
        DocumentMetaData documentMetaData = DocumentMetaData.builder()
                .author(pdfDocument.getDocumentInformation().getAuthor() == null ?
                        "Not Available" : pdfDocument.getDocumentInformation().getAuthor())
                .title(pdfDocument.getDocumentInformation().getTitle() == null ?
                        "Not Available" : pdfDocument.getDocumentInformation().getTitle())
                .creationDate(pdfDocument.getDocumentInformation().getCreationDate() == null ?
                        null : pdfDocument.getDocumentInformation().getCreationDate().getTime())
                .modifiedDate(pdfDocument.getDocumentInformation().getModificationDate() == null ?
                        null : pdfDocument.getDocumentInformation().getModificationDate().getTime())
                .pages(pdfDocument.getNumberOfPages())
                .build();

        return documentMetaData;
    }
}
