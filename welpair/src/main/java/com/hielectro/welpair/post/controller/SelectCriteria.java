package com.hielectro.welpair.post.controller;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SelectCriteria {

    private int currentPage;    // 현재페이지번호
    private int pageNo;         // 요청페이지
    private int limit;          // 한 페이지에 보여줄 게시물 수
    private int buttonAmount;   // 한 번에 보여줄 페이징 버튼 갯수
    private int maxPage;        // 가장 마지막 페이지
    private int starPage;       // 시작 페이지 번호
    private int endPage;        // 마지막 페이지 번호
    private int starRow;        // Db 조회시 최신 글 부터 조회해야하는 행의 시작 수
    private int endRow;         // Db 조회시 최신글 부터 조회해야하는 행의 마지막 수
//  private int totalMemberCount;
//  private int totalEmployeeCount;


    private String searchCondition; //검색 조건
    private String searchValue; //검색어

    public SelectCriteria(int pageNo, int totalCount, int limit, int buttonAmount, int maxPage, int starPage, int endPage, int starRow, int endRow) {



    }



}
