package com.hielectro.welpair.member.controller;

public class Pagenation {

    //검색어 없는 경우의 페이징 처리
//    public static SelectCriteria getSelectCriteria(int currentPage, int totalMemberCount, int itemsPerPage, int displayPageCount, String isExpired) {
    public static SelectCriteria getSelectCriteria(int currentPage, int listlength, int itemsPerPage, int displayPageCount, String isExpired) {

//        return getSelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount, null, null, isExpired);
        return getSelectCriteria(currentPage, listlength, itemsPerPage, displayPageCount, null, null, isExpired);
    }

    //검색어가 있는 경우 페이징 처리
//    public static SelectCriteria getSelectCriteria(int currentPage, int totalMemberCount, int itemsPerPage, int displayPageCount
    public static SelectCriteria getSelectCriteria(int currentPage, int listlength, int itemsPerPage, int displayPageCount
            , String searchCondition, String searchValue, String isExpired) {

        int totalPages;	//총 페이지 수이자 마지막 페이지 번호에 해당
        int startPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
        int endPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
        int startRow;
        int endRow;

        //총 페이지수 계산
//        totalPages = (int) Math.ceil((double) totalMemberCount / itemsPerPage);
        totalPages = (int) Math.ceil((double) listlength / itemsPerPage);
        System.out.println("총 항목 수를 토대로 계산된 총 페이지 수 : " + totalPages);

        //페이지번호 세트에서 시작페이지번호, 끝페이지번호
        startPage = (int) (Math.ceil((double) currentPage / displayPageCount) - 1) * displayPageCount + 1;
        endPage = startPage + displayPageCount - 1;


        //계산한 endPage보다 실제 마지막 페이지번호가 더 작은 경우엔 endPage에 실제 마지막 페이지번호를 대입
        if(totalPages < endPage){
            endPage = totalPages;
        }

        //항목이 없을 경우
        if(totalPages == 0 && endPage == 0) {
            totalPages = startPage;
            endPage = startPage;
        }

        //한 페이지에서 시작 항목의 인덱스, 끝 항목의 인덱스
        startRow = (currentPage - 1) * itemsPerPage + 1;  //(현재 페이지 번호 -1) * 페이지당 항목 수 + 1
        endRow = startRow + itemsPerPage - 1; //시작 인덱스 + 페이지당 항목 수 -1

        System.out.println("현재 페이지 번호 : " + currentPage);
        System.out.println("시작 항목의 인덱스 : " + startRow);
        System.out.println("끝 항목의 인덱스 : " + endRow);


//        SelectCriteria selectCriteria = new SelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount ,totalPages, startPage, endPage, startRow, endRow
        SelectCriteria selectCriteria = new SelectCriteria(currentPage, listlength, itemsPerPage, displayPageCount ,totalPages, startPage, endPage, startRow, endRow
                , searchCondition, searchValue, isExpired);

        return selectCriteria;
    }

}