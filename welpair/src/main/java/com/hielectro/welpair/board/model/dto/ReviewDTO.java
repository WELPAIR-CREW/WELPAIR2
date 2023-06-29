package com.hielectro.welpair.board.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {
    private String no;          // 게시글 번호
    private String pageNo;      // 페이지 번호
    private String orderNo;     // 주문 번호
    private int star;           // 별 개수
}
