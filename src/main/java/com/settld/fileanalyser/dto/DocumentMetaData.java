package com.settld.fileanalyser.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class DocumentMetaData {
    private String author;
    private Integer pages;
    private String title;
    private Date creationDate;
    private Date modifiedDate;
}
