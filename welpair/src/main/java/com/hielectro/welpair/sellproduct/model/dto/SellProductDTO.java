package com.hielectro.welpair.sellproduct.model.dto;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
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

    private ProductDTO product;
    private List<SellItemPageDTO> sellItemPageList;
}
