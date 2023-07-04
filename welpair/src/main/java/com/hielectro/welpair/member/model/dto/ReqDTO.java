package com.hielectro.welpair.member.model.dto;

import lombok.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReqDTO {
    private String empNo;
    private Date reqDate;
    private String isRegist;

    private EmployeeDTO employee;

    //simpleDateFomat을 getter에다가
//    public void setStartDate(String startDate) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            this.startDate = format.parse(startDate);
//        } catch (ParseException e) {
//            this.startDate = null;
//        }
//    }
//
//    public void setEndDate(String endDate) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            this.endDate = format.parse(endDate);
//        } catch (ParseException e) {
//            this.endDate = null;
//        }
//    }
}
