package com.hielectro.welpair.board.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private String no;
    private String category;
    private String title;
    private String content;
    private Date date;
    private String empNo;
    private String isPrivate;
}
