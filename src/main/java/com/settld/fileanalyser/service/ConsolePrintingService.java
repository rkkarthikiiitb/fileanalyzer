package com.settld.fileanalyser.service;

import com.settld.fileanalyser.dto.Document;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class ConsolePrintingService {
    private static final Integer NO_OF_COLUMNS = 3;

    public void printContractInformation(Document document) {
        printDocumentMetdaData(document);
        printDocumentData(document);
    }

    private void printDocumentData(Document document) {
        System.out.println(format("%s", "---------------------------------------------------------------------------------------------------------------------------"));
        System.out.println(format("%30s %25s %10s %25s %10s", "Page Number", "|", "# of fields required", "|", "# of fields filled"));
        document.getDocumentPages()
                .forEach(documentPage -> {
                    System.out.println(format("%s", "---------------------------------------------------------------------------------------------------------------------------"));
                    System.out.println(format("%30s %25s %10d %25s %10s", documentPage.getPageNumber(), "|",
                            documentPage.getExpectedFieldsRequiringValue(), "|", documentPage.getActualFieldsWithValue()));
                });
        System.out.println();
        System.out.println(format("%s", "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"));
        System.out.println(format("%s", "---------------------------------------------------------------------------------------------------------------------------"));
        System.out.println(format("%30s %25s %10s ", "Page Number", "|", "Extractable Data"));
        document.getDocumentPages()
                .forEach(documentPage -> {
                    System.out.println(format("%s", "---------------------------------------------------------------------------------------------------------------------------"));
                    System.out.println(format("%30s %25s %10s", documentPage.getPageNumber(), "|",
                            documentPage.getFilledFields().toString()));
                });

    }

    private void printDocumentMetdaData(Document document) {
        System.out.println(format("%30s %25s %10s", "Metadata", "|", "Value"));
        System.out.println(format("%30s %25s %10s%n", "Author", "|", document.getDocumentMetaData().getAuthor()));
        System.out.println(format("%30s %25s %10s%n", "Title", "|", document.getDocumentMetaData().getTitle()));
        System.out.println(format("%30s %25s %10d%n", "Pages", "|", document.getDocumentMetaData().getPages()));
        System.out.println(format("%30s %25s %10s%n", "Creation Date", "|", document.getDocumentMetaData().getCreationDate().toString()));
        System.out.println(format("%30s %25s %10s%n", "Modified Date", "|", document.getDocumentMetaData().getModifiedDate()));
    }
}
