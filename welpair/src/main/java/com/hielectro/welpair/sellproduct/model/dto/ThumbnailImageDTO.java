package com.hielectro.welpair.sellproduct.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThumbnailImageDTO {
    private String no;   // SellPage no
    private String thumbnailImageFileName;
    private String thumbnailImageOriginFileName;
}
