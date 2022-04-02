package com.settld.fileanalyser.service;

import com.settld.fileanalyser.dto.Document;
import com.settld.fileanalyser.dto.DocumentMetaData;
import com.settld.fileanalyser.exception.AnalysisOrchestratorException;
import com.settld.fileanalyser.exception.ValidationException;
import com.settld.fileanalyser.validator.DocumentValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@AllArgsConstructor
public class AnalysisOrchestratorService {

    private final DocumentValidator documentValidator;
    private final MetadataService metadataService;
    private final DocumentDataService documentDataService;
    private final ConsolePrintingService consolePrintingService;


    public void initiateDocumentAnalysis(String fileName) throws AnalysisOrchestratorException, IOException, ValidationException {
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            PDDocument pdfDocument = PDDocument.load(fileInputStream);

            log.info("Initiating Step 1 - Document Validation Check ");
            documentValidator.verifyDocumentIntegrity(pdfDocument, stream);

            log.info("Initiating Step 2 - Reading Document Metadata");
            DocumentMetaData documentMetaData = metadataService.extractMetaDataInformation(pdfDocument);

            log.info("Initiating Step 3 - Reading Document Data ");
            Document contractData = documentDataService.extractDocumentData(pdfDocument);
            contractData.setDocumentMetaData(documentMetaData);

            log.info("Initiating Step 4 - Print Analysis");
            consolePrintingService.printContractInformation(contractData);
            pdfDocument.close();
        } catch (IOException exception) {
            throw new ValidationException("Unsupported file format detected");
        }

    }
}
