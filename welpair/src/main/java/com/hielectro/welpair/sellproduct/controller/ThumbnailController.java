package com.hielectro.welpair.sellproduct.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private String rootPath = System.getProperty("user.dir");
    private String baseDir = "/src/main/resources/static";
    private String originalImageDir = "/common/images/original";
    private String thumbnailImageDir = "/common/images/thumbnail";
    private String absoluteOriginalImageDir = rootPath + baseDir + originalImageDir;
    private String absoluteThumbnailImageDir = rootPath + baseDir + thumbnailImageDir;

    public ThumbnailController(SellProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("add")
    public String registSellProduct(@ModelAttribute SellProductDTO sellProduct,
                                    @RequestParam String title,
                                    @ModelAttribute List<MultipartFile> uploadFiles,
                                    @ModelAttribute MultipartFile uploadDetailFile) {
        if (uploadFiles.size() > 6) {
            throw new IllegalStateException("상품 이미지는 최대 6개까지 등록 가능합니다.");
        }

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

    @PostMapping("modify")
    public String modifySellProduct(HttpServletRequest request,
                                    @ModelAttribute SellProductDTO sellProduct,
                                    @RequestParam String title,
                                    @ModelAttribute List<MultipartFile> uploadFiles,
                                    @ModelAttribute MultipartFile uploadDetailFile) {

        /* 이전에 저장되었던 Session의 결과값과 비교하여 수정된 부분이 있다면 Update 실행 */
        HttpSession session = request.getSession();
        SellProductDTO compareSellProduct = (SellProductDTO) session.getAttribute("productInfo");
        SellPageDTO compareSellPage = compareSellProduct.getSellItemPage().getSellPage();
        SellPageDTO sellPage = sellProduct.getSellItemPage().getSellPage();

        /* 업로드된 이미지 파일이 0개이고 제목이 같고 할인율이 동일하면 return */
        if (uploadFiles.get(0).isEmpty() && uploadDetailFile.isEmpty()
                && compareSellProduct.getDiscount() == sellProduct.getDiscount()
                && compareSellPage.getTitle().equals(title)) {

            return "redirect:/products/" + sellPage.getNo();
        }

        // 기존에 입력되어 있던 이미지 파일들 삭제
        int cnt = deleteImageFiles(compareSellPage.getThumbnailImageList());
        cnt += deleteImageFile(compareSellPage.getDetailImageFileName()) ? 1 : 0;

        if (cnt > 0) {
            log.info("[ThumbnailController] " + cnt + "개의 사진 삭제 완료!");
        }

        System.out.println("uploadFilesSize : " + uploadFiles.size() + "--------------------------------");

        try {
            sellPage.setTitle(title);
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