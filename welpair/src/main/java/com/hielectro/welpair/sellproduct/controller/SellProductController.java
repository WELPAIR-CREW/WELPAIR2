package com.hielectro.welpair.sellproduct.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.*;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/sellproduct")
@Slf4j
public class SellProductController {
    private final SellProductServiceImpl productService;
    private final int limit = 10;
//    @Value("${image.image-dir}")
    private final String IMAGE_DIR = "/src/main/resources/static";

    public SellProductController(SellProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("{url}")
    public String defaultLocation(@PathVariable String url) {
        return "admin/sellproduct/" + url;
    }

    @GetMapping("add")
    public String addSellProduct() {
        return "admin/sellproduct/admin-add-product";
    }

    @PostMapping("add")
    public String registSellProduct(@ModelAttribute SellProductDTO sellProduct,
                                    @RequestParam String title,
                                    @ModelAttribute List<MultipartFile> uploadFiles,
                                    @ModelAttribute MultipartFile uploadDetailFile
                                    ) {
        if (uploadFiles.size() > 6) {
            throw new IllegalStateException("상품 이미지는 최대 6개까지 등록 가능합니다.");
        }
        String rootPath = System.getProperty("user.dir");
        String baseDir = IMAGE_DIR;
        String originalImageDir = "/common/images/original";
        String thumbnailImageDir = "/common/images/thumbnail";
        String absoluteOriginalImageDir = rootPath + baseDir + originalImageDir;
        String absoluteThumbnailImageDir = rootPath + baseDir + thumbnailImageDir;
        File dir = new File(absoluteOriginalImageDir);
        File dir2 = new File(absoluteThumbnailImageDir);

        if (!dir.exists() || !dir2.exists()) {
            dir.mkdirs();
            dir2.mkdirs();
        }

        List<ThumbnailImageDTO> thumbnailImageList = new ArrayList<>();
        SellPageDTO sellPage = new SellPageDTO();
        sellPage.setTitle(title);
        sellPage.setThumbnailImageList(thumbnailImageList);
        sellPage.setPath(baseDir);
        sellProduct.setSellItemPage(new SellItemPageDTO());
        sellProduct.getSellItemPage().setSellPage(sellPage);

        try {
            for (MultipartFile file : uploadFiles) {
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedFileName = UUID.randomUUID().toString().replace("-", "");
                String thumbnailSize360x = savedFileName + "_360x";

                file.transferTo(new File(absoluteOriginalImageDir + "/" + savedFileName + ext));
                Thumbnails.of(absoluteOriginalImageDir + "/" + savedFileName + ext).size(360, 360)
                        .toFile(absoluteThumbnailImageDir + "/" + thumbnailSize360x + ext);
                Thumbnails.of(absoluteOriginalImageDir + "/" + savedFileName  + ext).size(60, 60)
                        .toFile(absoluteThumbnailImageDir + "/" + savedFileName + "_60x" + ext);

                ThumbnailImageDTO thumbnailImage = new ThumbnailImageDTO();
                thumbnailImage.setThumbnailImageOriginFileName(originFileName);
                thumbnailImage.setThumbnailImageFileName(thumbnailSize360x + ext);

                thumbnailImageList.add(thumbnailImage);
            }

            String originFileName = uploadDetailFile.getOriginalFilename();
            String ext = originFileName.substring(originFileName.lastIndexOf("."));
            String savedFileName = UUID.randomUUID().toString().replace("-", "");

            uploadDetailFile.transferTo(new File(absoluteOriginalImageDir + "/" + savedFileName + ext));
            sellPage.setPath("/common/images/");
            sellPage.setDetailImageOriginFileName(originFileName);
            sellPage.setDetailImageFileName(savedFileName + ext);

            productService.insertSellProduct(sellProduct);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();

            int cnt = 0;
            for (int i = 0; i < thumbnailImageList.size(); i++) {
                ThumbnailImageDTO file = thumbnailImageList.get(i);

                File deleteFile = new File(absoluteOriginalImageDir + "/" + file.getThumbnailImageFileName());
                String ext = deleteFile.getName().substring(deleteFile.getName().lastIndexOf("."));
                boolean isDeleted1 = deleteFile.delete();

                File deleteThumbnail = new File(absoluteOriginalImageDir + "/"
                        + file.getThumbnailImageFileName().substring(0, file.getThumbnailImageFileName().lastIndexOf(".")) + "_60x" + ext);
                File deleteThumbnail2 = new File(absoluteOriginalImageDir + "/"
                        + file.getThumbnailImageFileName().substring(0, file.getThumbnailImageFileName().lastIndexOf(".")) + "_360x" + ext);
                boolean isDeleted2 = deleteThumbnail.delete();
                boolean isDeleted3 = deleteThumbnail2.delete();

                if ((isDeleted1 && isDeleted2) && isDeleted3) {
                    cnt++;
                }
            }

            if (cnt * 2 == thumbnailImageList.size()) {
                log.info("[ThumbnailController] 업로드에 실패한 모든 사진 삭제 완료!");
            }
        }
        return "redirect:/products/" + sellPage.getNo();
//        return "redirect:consumer/sellproduct/product-detail";
    }

    @PostMapping("optionList")
    @ResponseBody
    public List<ProductDTO> selectOptionList(@RequestBody ProductDTO product) {
        return productService.selectOptionList(product);
    }

    @PostMapping("productNameList")
    @ResponseBody
    public List<ProductDTO> selectProductName(@RequestBody ProductDTO product) {
        return productService.selectProductNameList(product);
    }

    @GetMapping("review")
    public String reviewLocation(HttpServletRequest request, Model model,
                                 @ModelAttribute Search search,
                                 @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String queryString = search.toString();
        String url = String.valueOf(request.getRequestURL()) + queryString;
        Map<String, Object> searchMap = new HashMap<>();
        System.out.println("==========================" + search);
        searchMap.put("search", search);
        searchMap.put("pageNo", currentPageNo);
        model.addAttribute("queryString", queryString);

        getPaging(model, currentPageNo, url, () -> productService.reviewSearchCount(searchMap));
        getSelectList(model, () -> productService.selectReviewList(searchMap));
        return "admin/sellproduct/review";
    }

    @GetMapping("QnA")
    public String qnaLocation(HttpServletRequest request, Model model,
                              @ModelAttribute Search search,
                              @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String queryString = search.toString();
        String url = String.valueOf(request.getRequestURL()) + queryString;
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("search", search);
        searchMap.put("pageNo", currentPageNo);
        model.addAttribute("queryString", queryString);

        getPaging(model, currentPageNo, url, () -> productService.qnaSearchCount(searchMap));
        getSelectList(model, () -> productService.selectQnAList(searchMap));
        return "admin/sellproduct/QnA";
    }

    private void getPaging(Model model, int currentPageNo,
                           String url, Supplier<Integer> searchCountSupplier) {
        Map<String, Integer> paging = null;

        if (!Pagination.getURL().equals(url)) {
            Pagination.init(url);
            int result = searchCountSupplier.get();
            paging = Pagination.paging(result, currentPageNo);
        } else {
            paging = Pagination.getParameter(currentPageNo);
        }

        model.addAttribute("paging", paging);

    }

    private void getSelectList(Model model, Supplier<List<?>> selectListSupplier) {
        List<?> list = selectListSupplier.get();

        model.addAttribute("list", list);
    }


    @PostMapping(value = "sellProductListAPI", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SellProductDetailDTO> sellProductList(@RequestBody Map<String, String> request) {
        System.out.println("request : " + request);
        List<SellProductDetailDTO> sellProductList = productService.selectProductList(request);
        System.out.println(sellProductList);
        return sellProductList;
    }

    @PostMapping(value = "sellProductCountAPI", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Integer> sellProductCount(@RequestBody(required = false) Map<String, String> request) {
        int result = productService.sellProductSearchCount(request);
        Map<String, Integer> response = pagination(result);

        return response;
    }

    @PostMapping("sellProductDeleteAPI")
    @ResponseBody
    public int sellProductDelete(@RequestBody List<String> request) {
        System.out.println(request);
        try {
            return productService.sellProductDelete(request);
        } catch (Exception e) {
            throw new IllegalStateException("error");
        }
    }

    @GetMapping("test")
    public String testMethod(@ModelAttribute Search search) {
        System.out.println("test : " + search);
        return "redirect:/sellproduct/review";
    }

    public Map<String, Integer> pagination(int length) {
        Map<String, Integer> response = new HashMap<>();
        int maxPageNo = (int) Math.ceil((double) length / limit);

        response.put("maxPageNo", maxPageNo);
        response.put("startPageNo", 1);
        response.put("endPageNo", 5);

        return response;
    }
}
