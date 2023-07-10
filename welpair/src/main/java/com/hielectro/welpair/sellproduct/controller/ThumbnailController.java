package com.hielectro.welpair.sellproduct.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/sellproduct")
@Slf4j
public class ThumbnailController {
    private final SellProductServiceImpl productService;
    private String rootPath = "C:/upload/";
    private String originalImageDir = "original";
    private String thumbnailImageDir = "thumbnail";
    private String absoluteOriginalImageDir = rootPath + originalImageDir;
    private String absoluteThumbnailImageDir = rootPath + thumbnailImageDir;

    public ThumbnailController(SellProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping("add")
    public String addSellProduct() {
        return "admin/sellproduct/admin-add-product";
    }

    @PostMapping("add")
    public String registSellProduct(@ModelAttribute SellProductDTO sellProduct,
                                    @RequestParam String title,
                                    @RequestParam String sellStatus,
                                    @ModelAttribute List<MultipartFile> uploadFiles,
                                    @ModelAttribute MultipartFile uploadDetailFile) {
        if (uploadFiles.size() > 6) {
            throw new IllegalStateException("상품 이미지는 최대 6개까지 등록 가능합니다.");
        }

        System.out.println("--------------------- add SellStatus ---------------------");
        System.out.println(sellStatus);
        System.out.println("--------------------- add SellStatus ---------------------");
        File dir = new File(absoluteOriginalImageDir);
        File dir2 = new File(absoluteThumbnailImageDir);

        if (!dir.exists() || !dir2.exists()) {
            dir.mkdirs();
            dir2.mkdirs();
        }

        List<ThumbnailImageDTO> thumbnailImageList = new ArrayList<>();
        SellPageDTO sellPage = new SellPageDTO();
        sellPage.setTitle(title);
        sellPage.setSellStatus(sellStatus);
        sellPage.setThumbnailImageList(thumbnailImageList);
        sellProduct.setSellItemPage(new SellItemPageDTO());
        sellProduct.getSellItemPage().setSellPage(sellPage);

        try {
            makeThumbnailImage(uploadFiles, sellPage);
            makeDetailImage(uploadDetailFile, sellPage);
            sellPage.setPath("/common/images/");
            productService.insertSellProduct(sellProduct);
        } catch (IllegalStateException | IOException e) {
            handleImageUploadFailure(e, sellPage);
        }
        return "redirect:/products/" + sellPage.getNo();
    }

    @GetMapping("modify/{sellPageNo}")
    public String modifySellProduct(Model model, HttpServletRequest request, @PathVariable String sellPageNo) {
        Map<String, String> map = new HashMap<>();

        map.put("sellPageNo", sellPageNo);
        SellProductDTO sellProduct = productService.selectOneSellProduct(sellPageNo);
        model.addAttribute("productInfo", sellProduct);

        /* 판매 상품 페이지가 수정되었는지 판단하기 위해 Session에 결과값을 저장 */
        HttpSession session = request.getSession();
        session.setAttribute("productInfo", sellProduct);
        System.out.println(session.getAttribute("productInfo"));
        return "admin/sellproduct/admin-modify-product";
    }

    @PostMapping("modify")
    public String modifySellProduct(HttpServletRequest request,
                                    @ModelAttribute SellProductDTO sellProduct,
                                    @RequestParam String title,
                                    @RequestParam String sellStatus,
                                    @ModelAttribute List<MultipartFile> uploadFiles,
                                    @ModelAttribute MultipartFile uploadDetailFile) {

        System.out.println("--------------------- modify SellStatus ---------------------");
        System.out.println(sellStatus);
        System.out.println("--------------------- modify SellStatus ---------------------");

        /* 이전에 저장되었던 Session의 결과값과 비교하여 수정된 부분이 있다면 Update 실행 */
        HttpSession session = request.getSession();
        SellProductDTO compareSellProduct = (SellProductDTO) session.getAttribute("productInfo");
        SellPageDTO compareSellPage = compareSellProduct.getSellItemPage().getSellPage();
        SellPageDTO sellPage = sellProduct.getSellItemPage().getSellPage();

        /* 업로드된 이미지 파일이 0개이고 제목이 같고 할인율이 동일하면 return */
        if (uploadFiles.get(0).isEmpty() && uploadDetailFile.isEmpty()
                && compareSellProduct.getDiscount() == sellProduct.getDiscount()
                && compareSellPage.getTitle().equals(title)
                && compareSellPage.getSellStatus().equals(sellStatus)) {

            return "redirect:/products/" + sellPage.getNo();
        }

        // 기존에 등록되어 있던 이미지 파일들 삭제
        // 1. 썸네일 이미지가 업로드 되었을 시 기존 썸네일 삭제
        if (!uploadFiles.get(0).isEmpty()) {
            int cnt = deleteImageFiles(compareSellPage.getThumbnailImageList());
            log.info("[ThumbnailController] " + cnt + "개의 Thumbnail 삭제 완료!");
        }

        // 2. 상세 이미지가 업로드 되었을 시 기존 상세 이미지 삭제
        if (!uploadDetailFile.isEmpty()) {
            int cnt = deleteImageFile(compareSellPage.getDetailImageFileName()) ? 1 : 0;
            log.info("[ThumbnailController] DetailImage 삭제 완료!");
        }

        System.out.println("uploadFilesSize : " + uploadFiles.size() + "--------------------------------");

        try {
            sellPage.setTitle(title);
            sellPage.setSellStatus(sellStatus);
            makeThumbnailImage(uploadFiles, sellPage);
            makeDetailImage(uploadDetailFile, sellPage);
            sellPage.setPath("/common/images/");
            productService.modifySellProduct(compareSellProduct, sellProduct);

        } catch (IllegalStateException | IOException e) {
            handleImageUploadFailure(e, sellPage);
        }

        session.removeAttribute("productInfo");
        return "redirect:/products/" + sellPage.getNo();
    }


    private int deleteImageFiles(List<ThumbnailImageDTO> imageList) {
        int cnt = 0;

        for (ThumbnailImageDTO image : imageList) {
            cnt += deleteImageFile(image.getThumbnailImageFileName()) ? 1 : 0;
        }

        return cnt;
    }

    private boolean deleteImageFile(String fileName) {
        File deleteFile = new File(absoluteOriginalImageDir + "/" + fileName);
        File deleteThumbnail = new File(absoluteThumbnailImageDir + "/" + fileName);

        return deleteFile.delete() && deleteThumbnail.delete();
    }

    private void makeThumbnailImage(List<MultipartFile> uploadFiles, SellPageDTO sellPage) throws IOException {
        List<ThumbnailImageDTO> thumbnailImageList = new ArrayList<>();

        for (MultipartFile file : uploadFiles) {
            ThumbnailImageDTO thumbnailImage = null;
            String originFileName = null;
            String ext = null;
            String savedFileName = null;
            String thumbnailSize360x = null;

            if (file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
                originFileName = file.getOriginalFilename();
                ext = originFileName.substring(originFileName.lastIndexOf("."));
                savedFileName = UUID.randomUUID().toString().replace("-", "");
                thumbnailSize360x = savedFileName + "_360x";
                file.transferTo(new File(absoluteOriginalImageDir + "/" + savedFileName + ext));
                Thumbnails.of(absoluteOriginalImageDir + "/" + savedFileName + ext).size(360, 360)
                        .toFile(absoluteThumbnailImageDir + "/" + thumbnailSize360x + ext);

                thumbnailImage = new ThumbnailImageDTO();
                thumbnailImage.setThumbnailImageOriginFileName(originFileName);
                thumbnailImage.setThumbnailImageFileName(thumbnailSize360x + ext);
            }

            thumbnailImageList.add(thumbnailImage);
        }

        sellPage.setThumbnailImageList(thumbnailImageList);
    }

    private void makeDetailImage(MultipartFile uploadDetailFile, SellPageDTO sellPage) {
        String originFileName = null;
        String ext = null;
        String savedFileName = null;

        try {
            if (uploadDetailFile.getOriginalFilename() != null && !uploadDetailFile.getOriginalFilename().equals("")) {
                originFileName = uploadDetailFile.getOriginalFilename();
                ext = originFileName.substring(originFileName.lastIndexOf("."));
                savedFileName = UUID.randomUUID().toString().replace("-", "");
                uploadDetailFile.transferTo(new File(absoluteOriginalImageDir + "/" + savedFileName + ext));
                sellPage.setDetailImageOriginFileName(originFileName);
                sellPage.setDetailImageFileName(savedFileName + ext);
            }
        } catch (IllegalStateException | IOException e) {
            handleImageUploadFailure(e, sellPage);
        }
    }

    private void handleImageUploadFailure(Exception e, SellPageDTO sellPage) {
        e.printStackTrace();

        int cnt = deleteImageFiles(sellPage.getThumbnailImageList());
        cnt += deleteImageFile(sellPage.getDetailImageFileName()) ? 1 : 0;
        log.info("[ThumbnailController] 업로드에 실패한 모든 사진 삭제 완료! (" + cnt + "개)");

    }
}