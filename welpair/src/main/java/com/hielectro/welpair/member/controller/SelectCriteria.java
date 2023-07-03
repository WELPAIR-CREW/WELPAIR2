package com.hielectro.welpair.member.controller;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SelectCriteria {

    private int currentPage; //현재페이지번호
    private int totalMemberCount; //총 항목의 수
    private int itemsPerPage; //한 페이지에 보여줄 항목의 수
    private int displayPageCount; //한번에 표시할 페이지번호의 수
    private int totalPages; //총 페이지 수
    private int startPage; //페이지번호 세트의 시작 페이지 번호
    private int endPage;
    private int startRow; //한 페이지에서 시작 항목의 행번호
    private int endRow;
    private String searchCondition; //검색 조건
    private String searchValue; //검색어

    private String isExpired;


//    private int totalEmployeeCount;
//    private Date hireDate;
}
