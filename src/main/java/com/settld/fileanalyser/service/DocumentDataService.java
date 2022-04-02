package com.settld.fileanalyser.service;

import com.settld.fileanalyser.dto.Document;
import com.settld.fileanalyser.dto.DocumentPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentDataService {

    public Document extractDocumentData(PDDocument pdfDocument) throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        PDPageTree pdPages = pdfDocument.getPages();
        int pageCounter = 1;
        List<DocumentPage> contractPages = new ArrayList<>();
        for (PDPage individualPage : pdPages) {
            //Parse data only when there is filled data
            DocumentPage contractPage = getContractDataMap(individualPage, pageCounter, pdfTextStripper, pdfDocument);
            pageCounter++;
            contractPages.add(contractPage);
        }
        return Document.builder().documentPages(contractPages).build();

    }

    private DocumentPage getContractDataMap(PDPage individualPage, int pageCounter,
                                            PDFTextStripper pdfTextStripper, PDDocument pdfDocument) throws IOException {
        //Read one page at a time
        pdfTextStripper.setStartPage(pageCounter);
        pdfTextStripper.setEndPage(pageCounter);
        String rawContent = pdfTextStripper.getText(pdfDocument);
        List<String> linesWithFillableData = Arrays.stream(rawContent.split("\\r?\\n"))
                .filter(line -> StringUtils.contains(line, ":"))
                .map(line -> StringUtils.remove(line, ":").stripTrailing())
                .collect(Collectors.toList());
        List<String> annotationContent = new ArrayList<>();
        if (individualPage.getAnnotations().size() > 0) {
            annotationContent = individualPage.getAnnotations()
                    .stream()
                    .map(PDAnnotation::getContents)
                    .collect(Collectors.toList());
        }
        return DocumentPage.builder()
                .expectedFieldsRequiringValue(linesWithFillableData.size())
                .pageNumber(pageCounter)
                .actualFieldsWithValue(individualPage.getAnnotations() == null ? 0 : individualPage.getAnnotations().size())
                .filledFields(annotationContent)
                .isPageAForm(linesWithFillableData.size() > 0)
                .build();
    }
}
