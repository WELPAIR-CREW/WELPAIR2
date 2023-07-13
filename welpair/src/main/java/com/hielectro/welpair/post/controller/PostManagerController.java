package com.hielectro.welpair.post.controller;


import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.dto.AdminBoardTypeDTO;
import com.hielectro.welpair.post.model.service.AdminBoardService;
import com.hielectro.welpair.post.model.service.AdminBoardServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/post/*")
public class PostManagerController {

    private final AdminBoardService adminBoardService;

    private final AdminBoardServiceImpl adminBoardServiceImpl;



    public PostManagerController(AdminBoardService adminBoardService, AdminBoardServiceImpl adminBoardServiceImpl) {
        this.adminBoardService = adminBoardService;


        this.adminBoardServiceImpl = adminBoardServiceImpl;
    }


    /* ************************* 글작성하기 ************************** */

    @GetMapping("/admin/board_write")
    public String PostWrite(Model model){
        return "/post/admin/board_write";
    }

    @PostMapping("/admin/board_write")
    public String PostSave(@ModelAttribute AdminBoardDTO adminBoardDTO, RedirectAttributes rttr) throws BoardException {

        System.out.println("===========================================CHECK==========================="+adminBoardDTO);
        adminBoardServiceImpl.PostSave(adminBoardDTO);
        rttr.addFlashAttribute("message","게시글등록성공");

        return "redirect:/post/admin/board_write";
    }



    /* ************************ 자주묻는 질문 ***************************** */

    @GetMapping("/member/board_ask")
    public ModelAndView AdminBoardList(HttpServletRequest request,
                                @RequestParam(value = "currentPage", defaultValue = "1") int pageNo
                                , ModelAndView model){


        int limit = 10;
        int buttonAmount = 5;

        int totalCount = adminBoardServiceImpl.selectTotalCount();

        model.addObject("totalCount", totalCount);

        /*  4. 리스트를 조회해 온다.
        * */
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

        List<AdminBoardDTO> adminBoardList = adminBoardServiceImpl.selectBoardList(selectCriteria);

        model.addObject("adminBoardList", adminBoardList);
        model.addObject("selectCriteria", selectCriteria);
        model.setViewName("post/member/board_ask");

        return model;
    }


    /* *********************공지사항********************* */
    @GetMapping("/member/board_Notice")
    public ModelAndView PostNotice(HttpServletRequest request,
                                   @RequestParam(value = "currentPage", defaultValue = "1") int pageNo
                            , ModelAndView model){

        int limit = 10;
        int buttonAmount = 5;

        int totalCount = adminBoardServiceImpl.selectTotalCount();

        model.addObject("totalCount", totalCount);

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

        List<AdminBoardDTO> adminNoticeList = adminBoardServiceImpl.selectNoticeList(selectCriteria);

        model.addObject("adminNoticeList", adminNoticeList);
        model.addObject("selectCriteria", selectCriteria);
        model.setViewName("post/member/board_Notice");


        return model;

    }

    /* ************************** 문의 *************************** */
    @GetMapping("/member/board_Qna")
    public ModelAndView BoardQnaList(HttpServletRequest request,
                                     @RequestParam(value = "currentPage", defaultValue = "1") int pageNo
                                    , ModelAndView model){

        int limit = 10;
        int buttonAmount = 5;

        int totalCount = adminBoardServiceImpl.selectTotalCount();

        model.addObject("totalCount", totalCount);


        // 리스트 조회
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(pageNo, totalCount, limit, buttonAmount);

        List<AdminBoardDTO> adminQnaList = adminBoardServiceImpl.selectQnaList(selectCriteria);

        model.addObject("adminQnaList", adminQnaList);
        model.addObject("selectCriteria", selectCriteria);
        model.setViewName("post/member/board_Qna");



        return model;

    }


    /* ******************** 유저 글쓰기 ******************* */
    @GetMapping("/member/board_memberWrite")
    public String MemberWrite(Model model){

        return "post/member/board_memberWrite";
    }

    @PostMapping("/member/board_memberWrite")
    public String MemberWriteSave(@ModelAttribute AdminBoardDTO adminBoardDTO, RedirectAttributes rttr) throws BoardException {

        System.out.println("===========================================CHECK==========================="+adminBoardDTO);
        adminBoardServiceImpl.MemberWriteSave(adminBoardDTO);
        rttr.addFlashAttribute("message","게시글등록성공");

        return "redirect:/post/member/board_memberWrite";
    }








    /* *************************** 게시판 읽기 *********************** */
    @GetMapping("/member/board_read")
    public String selectBoardDetail(HttpServletRequest request, Model model){

        String boardNo = String.valueOf(request.getParameter("boardNo"));
        String boardCate = String.valueOf(request.getParameter("boardCate"));

        AdminBoardDTO boardDetail = adminBoardServiceImpl.selectBoardDetail(boardNo);
//        AdminBoardTypeDTO boardType = adminBoardServiceImpl.selectBoardType(boardCate);
        model.addAttribute("board", boardDetail);
//        model.addAttribute("boardType", boardType);

        return "post/member/board_read";

    }


}


