package com.hielectro.welpair.sellproduct.model.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellImageDTO {
    private String no;
    private String thumbnailImageFileName;
    private String thumbnailImageOriginFileName;
    private String detailImageFileName;
    private String detailImageOriginFileName;
    private String path;
    private Date createDate;
}
