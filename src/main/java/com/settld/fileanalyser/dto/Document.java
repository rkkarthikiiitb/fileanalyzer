package com.settld.fileanalyser.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Document {
    private List<DocumentPage> documentPages;
    private DocumentMetaData documentMetaData;
}
