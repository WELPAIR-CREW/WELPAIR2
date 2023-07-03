package com.hielectro.welpair.board.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QnADTO {
    private String no;          // 게시글 번호
    private String pageNo;      // 페이지 번호
    private char isAnswer;      // 답변 여부
}
