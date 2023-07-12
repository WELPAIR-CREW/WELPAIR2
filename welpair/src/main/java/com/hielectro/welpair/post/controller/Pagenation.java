package com.hielectro.welpair.post.controller;



public class Pagenation {

    public static SelectCriteria getSelectCriteria (int pageNo, int totalCount, int limit, int buttonAmount){

        int maxPage;                // 최대 페이지
        int starPage;               // 시작하는 첫번째 버튼
        int endPage;                // 마지막으로 끝나는 버튼
        int starRow;                // 최신 글 부터 조회해야하는 행의 시작 수
        int endRow;                 // 최신글 부터 조회해야하는 행의 마지막 수

        /* 총 페이지 계산 */
        maxPage = (int) Math.ceil((double) totalCount / limit);

        /* 현재 페이지에서 보여줄 시작 페이지 수 (10개씩 보여지게) */
        starPage = (int) (Math.ceil((double) pageNo / buttonAmount) -1) * buttonAmount +1;

        /* 목록 아래쪽에 보여질 마지막 페이지 수 */
        endPage = starPage + buttonAmount -1;

        if(maxPage < endPage){
            endPage = maxPage;
        }


        /* 마지막 페이지는 0이 될 수 없기 때문에 게시물이 아무것도 존재하지 않으면 max페이지와 endPage는 0이된다. */
        if(maxPage == 0 && endPage == 0){
            maxPage = starPage;
            endPage = starPage;
        }

        /* 조회할 시작 번호와 마지막 행 번호를 계산 */
        starRow = (pageNo -1) * limit +1;
        endRow = starRow + limit -1;

        System.out.println("startRow : " + starRow);
        System.out.println("endRow : " + endRow);

        SelectCriteria selectCriteria = new SelectCriteria(pageNo, totalCount, limit, buttonAmount, maxPage, starPage, endPage, starRow, endRow);


        return selectCriteria;
    }


//    /* 페이징처리 */
//    public static SelectCriteria getSelectCriteria (int pageNo, int totalCount, int limit, int buttonAmount ){
//
//
//    }

}
