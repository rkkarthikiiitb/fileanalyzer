package com.settld.fileanalyser.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DocumentPage {
    private Integer pageNumber;
    private boolean isPageAForm;
    private Integer expectedFieldsRequiringValue;
    private Integer actualFieldsWithValue;
    private List<String> filledFields;
}
