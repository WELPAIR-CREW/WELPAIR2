package com.hielectro.welpair.board.model.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReviewManagerDTO {
    private String id;
    private String name;
    private String title;
    private String content;
    private Date date;
    private Time time;
}
