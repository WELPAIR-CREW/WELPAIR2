package com.hielectro.welpair.common;

import lombok.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Search {

    private String id;
    private String code;
    private String name;
    private String title;
    private String content;
    private int price;
    private Date startDate;
    private Date endDate;

    public void setPrice(int price) {
        this.price = price;
    }


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

    @Override
    public String toString() {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance();

        if (id != null && !id.isEmpty()) {
            builder.queryParam("id", id);
        }

        if (code != null && !code.isEmpty()) {
            builder.queryParam("code", code);
        }

        if (name != null && !name.isEmpty()) {
            builder.queryParam("name", name);
        }

        if (title != null && !title.isEmpty()) {
            builder.queryParam("title", title);
        }

        if (content != null && !content.isEmpty()) {
            builder.queryParam("content", content);
        }

        if (price != 0) {
            builder.queryParam("price", price);
        }

        if (startDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            builder.queryParam("startDate", sdf.format(startDate));
        }

        if (endDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            builder.queryParam("endDate", sdf.format(endDate));
        }

        return builder.encode().build().toString();
    }
}