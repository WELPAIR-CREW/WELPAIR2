package com.hielectro.welpair.sellproduct.model.dto;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellProductDTO {
    @Setter
    private String id;
    @Setter
    private String code;
    private double discount;
    @Setter
    private String isSell;

    @Setter
    private ProductDTO product;
    @Setter
    private SellItemPageDTO sellItemPage;

    public void setDiscount(Double discount) {
        if (discount != null)
            this.discount = discount;
    }
}
