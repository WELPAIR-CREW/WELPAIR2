package com.hielectro.welpair.member.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageHandler {
    int totalCnt; //총 항목 수
    int pageSize; //페이지당 항목 수
    int naviSize = 5; //페이지번호 표시 수
    int totalPage; //전체 페이지 수
    int page; //현재 페이지
    int beginPage; //네비게이션의 첫페이지
    int endPage;
    boolean showPrev; //이전페이지로 이동하는 링크 표시여부
    boolean showNext; //다음페이지로 이동하는 링크 표시여부

    //-------------------------추가
    int startRow;
    int endRow;

    public PageHandler(int totalCnt, int page) {
        this(totalCnt, page, 10);
    }

    public PageHandler(int totalCnt, int page, int pageSize) {
        this.totalCnt = totalCnt;
        this.page = page;
        this.pageSize = pageSize;

        //총 항목수를 기반으로 계산한 총 페이지 수 ----> 계산오류
        totalPage = (int) Math.ceil(totalCnt / (double) pageSize);
        System.out.println("총 항목 수를 토대로 계산된 총 페이지 수 : " + totalPage);


        //naviNum : 몇번째 페이지번호 세트인지(1~5 or 6~10)를 나타냄. 현재페이지가 6이면 2번째 세트임
        int naviNum = (int) Math.ceil(page / (double) naviSize);

        beginPage = (naviNum - 1) * naviSize + 1; //2세트이면 시작페이지번호는 6이 되고 3세트이면 시작페이지번호는 11이 된다
        endPage = Math.min(beginPage + naviSize - 1, totalPage); //두 값 중 작은것


        //boolean값
        showPrev = beginPage > 1;
        showNext = endPage < totalPage;


        //----------추가
        //한 페이지에서 시작 항목의 인덱스, 끝 항목의 인덱스
        startRow = (page - 1) * pageSize + 1; ////(현재 페이지 번호 -1) * 페이지당 항목 수 + 1
        endRow = startRow + pageSize - 1; ////시작 인덱스 + 페이지당 항목 수 -1

    }


}
