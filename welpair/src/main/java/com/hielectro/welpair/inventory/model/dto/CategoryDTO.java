package com.hielectro.welpair.inventory.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {

    private String categoryCode;
    private String categoryName;
    private String refCategoryCode;

    private CategoryDTO refCategory;
}
