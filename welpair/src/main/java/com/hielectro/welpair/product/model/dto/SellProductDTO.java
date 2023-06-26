package com.hielectro.welpair.product.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellProductDTO {
    private String id;
    private String code;
    private float discount;
    private String isSell;

    private List<SellItemPageDTO> sellItemPageList;
}
