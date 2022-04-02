package com.settld.fileanalyser.service;

import com.settld.fileanalyser.exception.ValidationException;
import com.settld.fileanalyser.validator.DocumentValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnalysisOrchestratorServiceTest {
    private static final String TEST_FILE_NAME = "LINCOLN_CONTRACT.pdf";
    private static final String TEST_ENCRYPTED_FILE_NAME = "test_encrypted.pdf";
    private static final String TEST_UNSUPPORTED_FILE_FORMAT_NAME = "test_unsupported_format.xlsx";

    @InjectMocks
    private AnalysisOrchestratorService analysisOrchestratorService;

    @Mock
    MetadataService metadataService;

    @Mock
    DocumentValidator documentValidator;

    @Mock
    DocumentDataService documentDataService;

    @Mock
    ConsolePrintingService consolePrintingService;

    @Test
    @DisplayName("Test to check successful document loading")
    void testdocumentLoading() throws IOException, ValidationException {
        when(metadataService.extractMetaDataInformation(any())).thenCallRealMethod();
        doCallRealMethod().when(documentValidator).verifyDocumentIntegrity(any(), any());
        doCallRealMethod().when(consolePrintingService).printContractInformation(any());
        when(documentDataService.extractDocumentData(any())).thenCallRealMethod();

        assertAll(() -> {
            assertDoesNotThrow(() -> analysisOrchestratorService.initiateDocumentAnalysis(TEST_FILE_NAME));
            verify(metadataService, times(1)).extractMetaDataInformation(any());
            verify(consolePrintingService, times(1)).printContractInformation(any());
            verify(documentValidator, times(1)).verifyDocumentIntegrity(any(), any());
            verify(documentDataService, times(1)).extractDocumentData(any());
        });


    }

    @Test
    @DisplayName("Password protected document throws exception while processing")
    void testdocumentLoading_password_protected_shouldThrowException() throws IOException, ValidationException {
        doCallRealMethod().when(documentValidator).verifyDocumentIntegrity(any(), any());

        assertAll(() -> {
            assertThrows(ValidationException.class, () ->
                    analysisOrchestratorService.initiateDocumentAnalysis(TEST_ENCRYPTED_FILE_NAME));
            verify(metadataService, times(0)).extractMetaDataInformation(any());
            verify(consolePrintingService, times(0)).printContractInformation(any());
            verify(documentValidator, times(1)).verifyDocumentIntegrity(any(), any());
            verify(documentDataService, times(0)).extractDocumentData(any());
        });

    }

    @Test
    @DisplayName("Unsupported files should throw validation exception")
    void testdocumentLoading_unsupported_format_shouldThrowException() {
        assertAll(() -> {
            assertThrows(ValidationException.class, () ->
                    analysisOrchestratorService.initiateDocumentAnalysis(TEST_UNSUPPORTED_FILE_FORMAT_NAME));
            verify(metadataService, times(0)).extractMetaDataInformation(any());
            verify(consolePrintingService, times(0)).printContractInformation(any());
            verify(documentValidator, times(0)).verifyDocumentIntegrity(any(), any());
            verify(documentDataService, times(0)).extractDocumentData(any());
        });

    }

}
