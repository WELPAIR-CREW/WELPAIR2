package com.hielectro.welpair.member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pagenation {


    private static int currentPage;
    private static int startPage = 1;
    private static int endPage = 5;
    private static int totalPages; //총 페이지 수 (마지막 페이지 번호)
    private static int listLength; //총 항목 수 totalMemberCount, totalEmployeeCount 등
    private static int itemsPerPage = 10; //페이지당 항목 수
    private static String url = "";



    //항목이 하나도 없을 경우
    public static void init(String requestURL) {
        currentPage = 1;
        startPage = 1;
        endPage = 5;
        totalPages = 1; //항목이 0개이면 총 페이지수가 1페이지가 될것
        listLength = 0; //항목이 0개
        url = requestURL; //String.valueOf(request.getRequestURL())
    }


    public static Map<String, Integer> paging(int listLength, int currentPage) {
        if(listLength == 0) { //항목이 0개이면.........???
            listLength++;
        }
        listLength = listLength; //필드에 인자로 들어온 값(컨트롤러가 DB에서 받은 총항목수)을 대입
        currentPage = currentPage;
        totalPages = (int) Math.ceil((double)listLength/itemsPerPage); //총 페이지수 계산

        Map<String, Integer> response = new HashMap<>(); //???

        return getResultMap(response); //???
    }






    //검색어가 있는 경우 페이징 처리
    public static SelectCriteria getSelectCriteria(int currentPage, int totalMemberCount, int itemsPerPage, int displayPageCount
                                                    , String searchCondition, String searchValue, String isExpired) {

        int totalPages;	//총 페이지 수이자 마지막 페이지 번호에 해당
        int startPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
        int endPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
        int startRow;
        int endRow;

        //총 페이지수 계산
        totalPages = (int) Math.ceil((double) totalMemberCount / itemsPerPage);
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


        SelectCriteria selectCriteria = new SelectCriteria(currentPage, totalMemberCount, itemsPerPage, displayPageCount ,totalPages, startPage, endPage, startRow, endRow
                                                            , searchCondition, searchValue, isExpired);

        return selectCriteria;
    }






//    public static SelectCriteria getSelectCriteria(int currentPage, int totalEmployeeCount, int itemsPerPage, int displayPageCount, Date hireDate) {
//
//        return getSelectCriteria(currentPage, totalEmployeeCount, itemsPerPage, displayPageCount, null, null, hireDate);
//    }
//
//    public static SelectCriteria getSelectCriteria(int currentPage, int totalEmployeeCount, int itemsPerPage, int displayPageCount
//            , String searchCondition, String searchValue, Date hireDate) {
//
//        int totalPages;	//총 페이지 수이자 마지막 페이지 번호에 해당
//        int startPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
//        int endPage;	//하나의 페이지에 보여질 페이지번호 세트에서 시작 페이지번호
//        int startRow;
//        int endRow;
//
//        //총 페이지수 계산
//        totalPages = (int) Math.ceil((double) totalEmployeeCount / itemsPerPage);
//        System.out.println("총 항목 수를 토대로 계산된 총 페이지 수 : " + totalPages);
//
//        //페이지번호 세트에서 시작페이지번호, 끝페이지번호
//        startPage = (int) (Math.ceil((double) currentPage / displayPageCount) - 1) * displayPageCount + 1;
//        endPage = startPage + displayPageCount - 1;
//
//
//        //계산한 endPage보다 실제 마지막 페이지번호가 더 작은 경우엔 endPage에 실제 마지막 페이지번호를 대입
//        if(totalPages < endPage){
//            endPage = totalPages;
//        }
//
//        //항목이 없을 경우
//        if(totalPages == 0 && endPage == 0) {
//            totalPages = startPage;
//            endPage = startPage;
//        }
//
//        //한 페이지에서 시작 항목의 인덱스, 끝 항목의 인덱스
//        startRow = (currentPage - 1) * itemsPerPage + 1;  //(현재 페이지 번호 -1) * 페이지당 항목 수 + 1
//        endRow = startRow + itemsPerPage - 1; //시작 인덱스 + 페이지당 항목 수 -1
//
//        System.out.println("현재 페이지 번호 : " + currentPage);
//        System.out.println("시작 항목의 인덱스 : " + startRow);
//        System.out.println("끝 항목의 인덱스 : " + endRow);
//
//
//        SelectCriteria selectCriteria = new SelectCriteria(currentPage, totalEmployeeCount, itemsPerPage, displayPageCount ,totalPages, startPage, endPage, startRow, endRow
//                , searchCondition, searchValue, hireDate);
//
//        return selectCriteria;
//    }


}
