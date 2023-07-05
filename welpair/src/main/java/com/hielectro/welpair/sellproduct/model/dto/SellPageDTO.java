package com.hielectro.welpair.sellproduct.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellPageDTO {
    private String no;  // Page Number
    private String title;  // Page Title;
    private String detailImageFileName;
    private String detailImageOriginFileName;
    private String path;
    private Date createDate;

    List<ThumbnailImageDTO> thumbnailImageList;
}
