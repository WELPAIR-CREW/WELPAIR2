package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hielectro.welpair.board.model.dto.BoardDTO;
import com.hielectro.welpair.board.model.dto.QnAManagerDTO;
import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SellProductServiceImpl implements SellProductService {
    private final SellProductMapper productMapper;

    public SellProductServiceImpl(SellProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<CategoryDTO> selectCategoryList() {
        return productMapper.selectCategoryList();
    }

    @Override
    public List<ProductDTO> selectProductStatus() {
        return productMapper.selectProductStatus();
    }

    @Override
    public int sellProductSearchCount(Map<String, String> search) {
        return productMapper.sellProductSearchCount(search);
    }

    @Override
    public int reviewSearchCount(Map<String, Object> searchMap) {
        return productMapper.reviewSearchCount(searchMap);
    }

    @Override
    public int qnaSearchCount(Map<String, Object> searchMap) {
        return productMapper.qnaSearchCount(searchMap);
    }

    @Override
    public List<ProductDTO> selectOptionList(ProductDTO product) {
        return productMapper.selectOptionList(product);
    }

    @Override
    public List<ProductDTO> selectProductNameList(ProductDTO product) {
        return productMapper.selectProductNameList(product);
    }

    @Override
    @Transactional
    public void insertSellProduct(SellProductDTO sellProduct) throws IllegalStateException {
        SellItemPageDTO sellItemPage = sellProduct.getSellItemPage();
        SellPageDTO sellPage = sellItemPage.getSellPage();
        List<ThumbnailImageDTO> thumbnailImageList = sellPage.getThumbnailImageList();

        // 1. 판매상품 추가
        {
            int result = productMapper.insertSellProduct(sellProduct);
            System.out.println(result);
            if (!(result > 0)) {
                throw new IllegalStateException("SellProduct insert Failed");
            }
        }
        // 2. 판매상품 페이지 추가
        {
            int result = productMapper.insertSellPage(sellPage);

            if (!(result > 0)) {
                throw new IllegalStateException("SellPage insert Failed");
            }
        }
        // 3. 판매상품별페이지 추가
        {
            sellItemPage.setId(sellProduct.getId());
            sellItemPage.setNo(sellPage.getNo());
            int result = productMapper.insertSellItemPage(sellItemPage);

            if (!(result > 0)) {
                throw new IllegalStateException("SellItemPage insert Failed");
            }
        }
        // 4. 페이지의 썸네일 추가
        {
            int result = 0;

            for (ThumbnailImageDTO thumbnail : thumbnailImageList) {
                if (thumbnail == null) return;

                thumbnail.setNo(sellPage.getNo());
                result += productMapper.insertThumbnail(thumbnail);
            }

            if (result != thumbnailImageList.size()) {
                throw new IllegalStateException("thumbnail insert Failed");
            }
        }
    }

    public SellProductDTO selectOneSellProduct(String pageNo) {
        return productMapper.selectOneSellProduct(pageNo);
    }

    @Override
    public List<SellProductDetailDTO> selectProductList(Map<String, String> productId) {
        return productMapper.selectProductList(productId);
    }

    @Override
    public List<ReviewManagerDTO> selectReviewList(Map<String, Object> searchMap) {
        return productMapper.selectReviewList(searchMap);
    }

    @Override
    public List<QnAManagerDTO> selectQnAList(Map<String, Object> searchMap) {
        return productMapper.selectQnAList(searchMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int sellProductDelete(List<String> request) throws IllegalStateException {
        int size = request.size();
        int result = 0;

        System.out.println("service : " + request);
        for (int i = 0; i < size; i++) {
            result += productMapper.sellProductDelete(request.get(i));
        }

        if (result != size) {
            throw new IllegalStateException();
        }

        return result;
    }

    @Override
    @Transactional
    public void modifySellProduct(SellProductDTO compareSellProduct, SellProductDTO sellProduct) {
        SellPageDTO compareSellPage = compareSellProduct.getSellItemPage().getSellPage();
        SellPageDTO sellPage = sellProduct.getSellItemPage().getSellPage();
        List<ThumbnailImageDTO> thumbnailImageList = sellPage.getThumbnailImageList();
        List<ThumbnailImageDTO> compareThumbnailImageList = compareSellPage.getThumbnailImageList();

        System.out.println("modifySellProduct : " + sellPage + "-------------------------");

        // 1. 판매상품 페이지 수정
        String compareTitle = compareSellPage.getTitle();
        String sellTitle = sellPage.getTitle();
        String compareDetailImageFileName = Optional.ofNullable(compareSellPage.getDetailImageFileName()).orElse("");
        String sellDetailImageFileName = sellPage.getDetailImageFileName();

        boolean isPageModificationNeeded = !compareTitle.equals(sellTitle) || !compareDetailImageFileName.equals(sellDetailImageFileName);

        if (isPageModificationNeeded) {
            log.info("페이지 수정");
            int result = productMapper.updateSellPage(sellPage);

            if (result <= 0) {
                throw new IllegalStateException("SellPage Update Failed");
            }
        }

        // 2. 판매상품 수정
        if (compareSellProduct.getDiscount() != sellProduct.getDiscount()) {
            int result = productMapper.updateSellProduct(sellProduct);

            if (!(result > 0)) {
                throw new IllegalStateException("SellProduct Update Failed");
            }
        }

        // 3. 페이지의 썸네일 삭제
        log.info("compareThumbnailImageList : " + compareThumbnailImageList);
        Optional<String> thumbnailImageFileNameOptional = Optional.ofNullable(compareThumbnailImageList)
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0).getThumbnailImageFileName());

        if (thumbnailImageFileNameOptional.isPresent()) {
            log.info("썸네일 삭제");
            int result = 0;
            result += productMapper.deleteThumbnail(sellPage.getNo());

            if (result != compareThumbnailImageList.size()) {
                throw new IllegalStateException("Thumbnail Delete Failed");
            }
        }

        // 4. 페이지의 썸네일 추가
        if (sellPage.getThumbnailImageList() != null) {
            log.info("썸네일 추가");
            int result = 0;

            for (ThumbnailImageDTO thumbnail : thumbnailImageList) {
                if (thumbnail == null) return;

                thumbnail.setNo(sellPage.getNo());
                result += productMapper.insertThumbnail(thumbnail);
            }

            if (result != thumbnailImageList.size()) {
                throw new IllegalStateException("Thumbnail Insert Failed");
            }
        }
    }

    @Override
    public boolean setPrivateBoard(List<BoardDTO> boardList) {
        int result = 0;
        for (var board : boardList) {
            result += productMapper.updateReview(board);
        }

        if (boardList.size() != result) {
            throw new IllegalStateException("Review 비공개 실패");
        }

        return true;
    }
}
