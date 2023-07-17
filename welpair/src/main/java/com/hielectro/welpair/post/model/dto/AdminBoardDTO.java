package com.hielectro.welpair.post.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminBoardDTO {

    private String boardNo;                     // 글번호
    private String boardCategory;               // 글 유형 넘버
    private String boardTitle;                  // 글 제목
    private String boardContent;                // 글 내용
    private Date boardDate;                     // 작성일
    private String empNo;                       // 작성자 사번
    private String isPrivate;                   // 공개여부 > CHAR -> 'N'

//    private AdminBoardTypeDTO boardType;        // 카테고리 타입

    List<AdminBoardTypeDTO> typeList;

}

