package com.hielectro.welpair.post.controller;


import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
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




//    @GetMapping("/member/board_ask")
//    public String PostAsk(Model model){
//        return "/post/member/board_ask";
//    }
//




    /* *********************공지사항********************* */
    @GetMapping("/member/board_Notice")
    public String PostNotice(Model model){ return "/post/member/board_Notice"; }



    /* ************************** 문의 *************************** */
    @GetMapping("/member/board_Qna")
    public String PostQna(Model model){ return "/post/member/board_Qna"; }




}


