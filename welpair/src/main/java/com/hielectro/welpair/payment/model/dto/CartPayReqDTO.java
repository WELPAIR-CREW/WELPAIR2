package com.hielectro.welpair.payment.model.dto;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartPayReqDTO {

    private List<CartGeneralDTO> cartPayList;
}
