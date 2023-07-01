package com.hielectro.welpair.board.model.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QnAManagerDTO {
    private String no;
    private String pageNo;
    private String title;
    private String content;
    private Date date;
    private Time time;
}
