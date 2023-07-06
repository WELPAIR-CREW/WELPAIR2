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
    private final String IMAGE_DIR = "/src/main/resources/static";

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
                String ext = null;
                if (originFileName != null && !originFileName.equals("")) {
                    
                    ext = originFileName.substring(originFileName.lastIndexOf("."));
                }
                String savedFileName = UUID.randomUUID().toString().replace("-", "");
                String thumbnailSize360x = savedFileName + "_360x";

                file.transferTo(new File(absoluteOriginalImageDir + "/" + savedFileName + ext));
                Thumbnails.of(absoluteOriginalImageDir + "/" + savedFileName + ext).size(360, 360)
                        .toFile(absoluteThumbnailImageDir + "/" + thumbnailSize360x + ext);

                ThumbnailImageDTO thumbnailImage = new ThumbnailImageDTO();
                thumbnailImage.setThumbnailImageOriginFileName(originFileName);
                thumbnailImage.setThumbnailImageFileName(thumbnailSize360x + ext);

                thumbnailImageList.add(thumbnailImage);
            }

            String originFileName = uploadDetailFile.getOriginalFilename();
            String ext = null;
            if (originFileName != null && !originFileName.equals("")) {

                ext = originFileName.substring(originFileName.lastIndexOf("."));
            }
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
                        + file.getThumbnailImageFileName().substring(0,
                                file.getThumbnailImageFileName().lastIndexOf("."))
                        + "_360x" + ext);
                boolean isDeleted2 = deleteThumbnail.delete();

                if (isDeleted1 && isDeleted2) {
                    cnt++;
                }
            }

            if (cnt * 2 == thumbnailImageList.size()) {
                log.info("[ThumbnailController] 업로드에 실패한 모든 사진 삭제 완료!");
            }
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
        SellProductDTO compare = (SellProductDTO) session.getAttribute("productInfo");
        SellPageDTO compareSellPage = compare.getSellItemPage().getSellPage();
        SellPageDTO sellPage = sellProduct.getSellItemPage().getSellPage();

        if (compareSellPage.getTitle().equals(sellPage.getTitle()))

            System.out.println("-------------------------------Thumbnail Controller -------------------------");
        System.out.println();

        session.removeAttribute("productInfo");
        return "";
    }
}
