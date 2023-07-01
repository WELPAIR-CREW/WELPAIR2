package com.hielectro.welpair.common;

import lombok.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Search {

    private String id;
    private String code;
    private String name;
    private String title;
    private String content;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private Date startDate;
    private Date endDate;

    public void setId(String id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartDate(String startDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.startDate = format.parse(startDate);
        } catch (ParseException e) {
            this.startDate = null;
        }
    }

    public void setEndDate(String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.endDate = format.parse(endDate);
        } catch (ParseException e) {
            this.endDate = null;
        }
    }
}
