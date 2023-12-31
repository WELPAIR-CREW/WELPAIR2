package com.hielectro.welpair.search.model.dto;

import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchDTO {

    private int minPrice;
    private int maxPrice;
    private int sellPrice;
    private String sortType;
    private int pageNo;

    private SellPageDTO sellPage;
    private ThumbnailImageDTO thumbnailImage;
    private SellItemPageDTO sellItemPage;
    private SellProductDTO sellProduct;
    private ProductDTO product;
    private CategoryDTO category;
}
